package impl

import Action
import Applicable
import Effect
import Fluent
import FluentBasedGoal
import Plan
import Planner
import Problem
import State
import VariableAssignment
import java.util.*

internal class StripsPlanner : Planner {
    override fun plan(problem: Problem): Sequence<Plan> {
        return plan(
            problem.initialState,
            problem.domain.actions,
            problem.goal as FluentBasedGoal
        )
    }

    private fun Stack<Applicable<*>>.apply(substitution: VariableAssignment){
        val i = listIterator()
        while(i.hasNext()){
            val x= i.next()
            i.set(x.apply(substitution))
        }
    }

    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal
    ): Sequence<Plan> = sequence {
        var currentState = initialState
        val stack = Stack<Applicable<*>>().also { it.push(goal) }
        val plan = mutableListOf<Action>()
        while (stack.isNotEmpty()) {
            val head = stack.peek()
            when {
                head is Fluent -> {
                    if (currentState.fluents.any { it.match(head) }) {
                        val substitution =
                            currentState.fluents.filter { it.match(head) }.map { it.mostGeneralUnifier(head) }
                        stack.pop()
                        stack.apply(substitution.first())
                        //TODO("applica la sostituzione a tutto lo stack")
                    } else {
                        val h = Effect.of(head)
                        stack.pop()
                        val action = actions.first { a -> a.positiveEffects.any { p -> p.match(h) } }
                        stack.push(action)
                        stack.addAll(action.preconditions)
                        val effect = action.positiveEffects.first { p -> p.match(h) }
                        stack.apply(h.mostGeneralUnifier(effect))
                        //TODO("retrieve dell'azione - push dell'azione -push delle precondizioni dell'azione")
                    }
                }
                (head is FluentBasedGoal) -> {
                    stack.pop()
                    for (fluent in head.targets)
                        stack.push(fluent)
                }
                (head is Action) -> {
                    stack.pop()
                    currentState = currentState.apply(head).first()
                    plan.add(head)
                    //TODO("applicare l'azione a currentState e aggiornarlo")
                }
                else -> {
                    // do nothing
                }
            }
        }
        yield(Plan.of(plan))
    }
}
