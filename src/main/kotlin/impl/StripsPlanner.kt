package impl

import Action
import Effect
import Fluent
import FluentBasedGoal
import Plan
import Planner
import Problem
import State
import java.util.*

internal class StripsPlanner : Planner {
    override fun plan(problem: Problem): Sequence<Plan> {
        return plan(
            problem.initialState,
            problem.domain.actions,
            problem.goal as FluentBasedGoal
        )
    }

    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal
    ): Sequence<Plan> = sequence {
        var currentState = initialState
        val stack = Stack<Any>().also { it.push(goal) }
        val plan = mutableListOf<Action>()
        while (stack.isNotEmpty()) {
            when (val head = stack.peek()) {
                head is Fluent && currentState.fluents.any { it.match(head) } -> {
                    TODO("applica la sostituzione a tutto lo stack")
                }
                head is Effect && actions.any { a -> a.positiveEffects.any { it.match(head) } } -> {
                    TODO("retrieve dell'azione")
                    TODO("push dell'azione")
                    TODO("push delle precondizioni dell'azione")
                }
                head is FluentBasedGoal -> {
                    stack.pop()
                    for (fluent in (head as FluentBasedGoal).targets) {
                        stack.push(fluent)
                    }
                }
                head is Action -> {
                    TODO("applicare l'azione a currentState e aggiornarlo")
                    plan.add(head as Action)
                }
                else -> {
                    // do nothing
                }
            }
        }
        yield(Plan.of(plan))
    }
}