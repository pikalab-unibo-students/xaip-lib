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
    data class ChoicePoint(val stack: Stack<Applicable<*>>, val state:State)
    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal
    ): Sequence<Plan> = sequence {
        var currentState = initialState
        var stack = Stack<Applicable<*>>().also { it.push(goal) }

        val choicePoints : Deque<ChoicePoint> = LinkedList()

        val plan = mutableListOf<Action>()
        while(true){
            while (stack.isNotEmpty()) {
                val head = stack.peek()
                when {
                    head is Fluent -> {
                        if (currentState.fluents.any { it.match(head) }) {
                            val substitutions =
                                currentState.fluents.filter { it.match(head) }.map { it.mostGeneralUnifier(head) }
                            stack.pop()
                            val substitution= substitutions.first()
                            substitutions.drop(1)
                            for (elem in substitutions) {
                                val stackCopy=stack
                                stackCopy.apply(elem)
                                choicePoints.add(ChoicePoint(stackCopy, currentState))
                            }
                            stack.apply(substitution)
                            //TODO("applica la sostituzione a tutto lo stack")
                        } else {
                            val h = Effect.of(head)
                            stack.pop()
                            val actionsMatched=
                                actions.filter{a -> a.positiveEffects.any { p -> p.match(h) }}
                            //val action = actions.first { a -> a.positiveEffects.any { p -> p.match(h) } }
                            val action=actionsMatched.first()
                            actionsMatched.drop(1)
                            for(elem in actionsMatched){
                                val stackCopy=stack
                                stackCopy.push(elem)
                                choicePoints.add(ChoicePoint(stackCopy, currentState))
                            }
                            stack.push(action)
                            stack.addAll(action.preconditions)
                            val effectsMatched=
                                action.positiveEffects.filter { p -> p.match(h) }
                            //val effect = action.positiveEffects.first { p -> p.match(h) }
                            val effect= effectsMatched.first()
                            effectsMatched.drop(1)
                            for(elem in effectsMatched){
                                val stackCopy=stack
                                stackCopy.apply(h.mostGeneralUnifier(elem))
                                choicePoints.add(ChoicePoint(stackCopy, currentState))
                            }
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
                        //currentState = currentState.apply(head).first()
                        val states = currentState.apply(head).toList()// necessario perché se no si arrabbia e mi dice la seq può essere iterata solo una volta
                        currentState=states.first()
                        states.drop(1)
                        for (elem in states){
                            choicePoints.add(ChoicePoint(stack, elem))
                        }
                        plan.add(head)
                        //TODO("applicare l'azione a currentState e aggiornarlo")
                    }
                    else -> {
                        break
                    }
                }
            }
            yield(Plan.of(plan))
            //STO grigio non ci dovrebbe essere
            if(choicePoints.peekFirst()!=null) {
                val choicePoint=choicePoints.pollFirst()
                stack = choicePoint.stack
                currentState = choicePoint.state
            }else{
                return@sequence
            }
        }
    }
}
