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
            problem.goal as FluentBasedGoal,
            emptyList()
        )
    }

    private fun plan(
        currentState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal,
        partialPlan: List<Action>
    ): Sequence<Plan> = sequence {
        val stack = Stack<Any>().also { it.push(goal) }
        while (stack.isNotEmpty()) {
            when (val head = stack.peek()) {
                head is Fluent && currentState.fluents.any { it.match(head) } -> {
                    TODO()
                }
                head is Effect && actions.any { a -> a.positiveEffects.any { it.match(head) } } -> {
                    TODO()
                }
                head is FluentBasedGoal -> {
                    stack.pop()
                    for (fluent in head.targets) {

                    }
                }
                head is Action -> {
                    TODO()
                }
                else -> {
                    // do nothing
                }
            }
        }
    }
}
