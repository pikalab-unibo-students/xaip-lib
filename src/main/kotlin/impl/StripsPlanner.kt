package impl

import Action
import Effect
import Fluent
import FluentBasedGoal
import Plan
import Planner
import Problem
import State
import VariableAssignment
import it.unibo.tuprolog.utils.subsequences
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
                    var substitution= VariableAssignment.empty()
                    currentState.fluents.forEach {
                        if(it.match(head as Fluent)){
                            substitution=it.mostGeneralUnifier(head)
                        }
                    }
                    for(elem in stack){
                        if (elem is FluentBasedGoal)
                            elem.apply(substitution)
                        else if(elem is Action)
                            elem.apply(substitution)
                    }
                    TODO("applica la sostituzione a tutto lo stack")
                }
                head is Effect && actions.any { a -> a.positiveEffects.any { it.match(head) } } -> {
                    stack.pop()
                    actions.forEach { a ->
                        a.positiveEffects.forEach {
                            if (it.match(head as Effect)) {
                                stack.push(a)
                                a.preconditions.forEach { p -> stack.push(p) }
                                if (it.match(head)) {
                                    stack.push(it)
                                }
                            }
                            for (elem in stack)
                                if (elem is FluentBasedGoal)
                                    elem.apply(it.mostGeneralUnifier(head))
                        }
                        TODO("retrieve dell'azione")
                        TODO("push dell'azione")
                        TODO("push delle precondizioni dell'azione")
                    }
                }
                head is FluentBasedGoalImpl -> {//non entra sebbene la testa dovrebbe essere un fluentBasedGoalImpl
                    stack.pop()
                    for (fluent in (head as FluentBasedGoal).targets) {
                        stack.push(fluent)
                    }
                }
                head is Action -> {
                    //perché non devo rimuovere l'azione? Come faccio a terminare senza rimuoverla?
                    currentState.apply(head as Action)
                    //in che senso aggiornarlo
                    plan.add(head)
                    TODO("applicare l'azione a currentState e aggiornarlo")
                }
                else -> {
                    // do nothing
                }
            }
        }
        yield(Plan.of(plan))
    }
}
