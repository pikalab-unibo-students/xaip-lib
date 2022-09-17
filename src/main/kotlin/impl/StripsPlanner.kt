package impl

import Action
import Fluent
import FluentBasedGoal
import Operator
import Plan
import Planner
import Problem
import State

internal class StripsPlanner : Planner {

    companion object {
        private const val DEBUG = true

        private fun log(msg: () -> String) {
            if (DEBUG) {
                println(msg())
                System.out.flush()
            }
        }
    }

    override fun plan(problem: Problem): Sequence<Plan> = sequence {
        if (problem.domain.axioms != null) error("Axioms are not yet supported")
        var depth = 1
        val set = mutableSetOf<Plan>()
        while (true) {
            val originalSize = set.size
            for (p in plan(problem.initialState, problem.domain.actions, problem.goal as FluentBasedGoal, depth++)) {
                if (p !in set) {
                    yield(p)
                    set.add(p)
                }
            }
            if (originalSize > 0 && set.size == originalSize) {
                break
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal,
        maxDepth: Int
    ): Sequence<Plan> = sequence {
        with(ExecutionContext(initialState, goal, maxDepth)) {
            while (true) {
                while (stack.isNotEmpty()) {
                    log { this.toString() }
                    val head = stack.pop()
                    when {
                        head is Fluent -> { // "applica la sostituzione a tutto lo stack"
                            if (currentState.fluents.any { it.match(head) }) {
                                handleFluentInCurrentState(head)
                            } else if (handleFluentNotInCurrentState(head, actions)) return@sequence
                        }
                        (head is FluentBasedGoal) -> {
                            stack.addAll(head.targets)
                        }
                        (head is Operator) -> { // applicare l'azione a currentState e aggiornarlo"
                            if (handleAction(head)) return@sequence
                        }
                        else -> {
                            error("Handle the case where $head is ${head::class}(probably via backtracking)")
                        }
                    }
                }
                yield(Plan.of(plan))
                if (backtrackOrFail()) return@sequence
            }
        }
    }.distinct()
}
