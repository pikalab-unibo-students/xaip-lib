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

    override fun plan(problem: Problem): Sequence<Plan> = sequence {
        if (problem.domain.axioms.isNotEmpty()) error("Axioms are not yet supported")
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

    private fun Stack<Applicable<*>>.apply(substitution: VariableAssignment) {
        val iterator = listIterator()
        while (iterator.hasNext()) {
            val x = iterator.next()
            iterator.set(x.apply(substitution))
        }
    }

    private fun Set<Action>.`actions whose effects match head`(head: Effect) =
        map { it.refresh() }.filter { action -> action.positiveEffects.any { effect -> effect.match(head) } }

    private data class ChoicePoint(val stack: Stack<Applicable<*>>, val state: State, val plan: MutableList<Action>)

    private fun Stack<Applicable<*>>.update(elem: Any, h: Effect? = null) {
        when (elem) {
            is VariableAssignment -> {
                this.apply(elem)
            }
            is Action -> {
                this.push(elem)
                this.addAll(elem.preconditions)
            }
            is Effect -> {
                this.apply(h!!.mostGeneralUnifier(elem))
            }
        }
    }

    private fun Deque<ChoicePoint>.update( // Perché non devo ritornare i punti di scelta?
        changes: List<Any>,
        stack: Stack<Applicable<*>>,
        currentState: State,
        plan: List<Action>,
        effect: Effect? = null
    ) {
        for (elem in changes.asSequence().drop(1)) {
            @Suppress("UNCHECKED_CAST")
            val stackCopy = stack.clone() as Stack<Applicable<*>>
            when (elem) {
                is VariableAssignment -> {
                    stackCopy.apply(elem)
                }
                is Action -> {
                    stackCopy.push(elem)
                    stackCopy.addAll((elem).preconditions)
                }
                is Effect -> {
                    stackCopy.apply(effect!!.mostGeneralUnifier(elem))
                }
            }
            this.addLast(
                ChoicePoint(stackCopy, currentState, plan.toMutableList())
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal,
        maxDepth: Int
    ): Sequence<Plan> = sequence<Plan> {
        var currentState = initialState
        var stack = Stack<Applicable<*>>().also { it.push(goal) }
        val choicePoints: Deque<ChoicePoint> = LinkedList()
        var plan = mutableListOf<Action>()

        fun backtrackOrFail(): Boolean {
            if (!choicePoints.isEmpty()) {
                val choicePoint = choicePoints.pollFirst()
                stack = choicePoint.stack
                currentState = choicePoint.state
                plan = choicePoint.plan
                return false
            }
            return true
        }

        while (true) {
            while (stack.isNotEmpty()) {
                val head = stack.peek()
                stack.pop()
                when {
                    head is Fluent -> { // "applica la sostituzione a tutto lo stack"
                        if (currentState.fluents.any { it.match(head) }) {
                            val substitutions =
                                currentState.fluents.filter { it.match(head) }.map { it.mostGeneralUnifier(head) }
                            choicePoints.update(substitutions, stack, currentState, plan)
                            stack.update(substitutions.first())
                        } else { // "retrieve dell'azione - push dell'azione -push delle precondizioni dell'azione"
                            val h = Effect.of(head)
                            val actionsMatched = actions.`actions whose effects match head`(h)
                            val action = actionsMatched.first()
                            choicePoints.update(actionsMatched, stack, currentState, plan)
                            stack.update(action, h)

                            val effectsMatched = action.positiveEffects.filter { effect -> effect.match(h) }
                            choicePoints.update(effectsMatched, stack, currentState, plan, h)
                            stack.update(effectsMatched.first(), h)
                        }
                    }
                    (head is FluentBasedGoal) -> {
                        stack.addAll(head.targets)
                    }
                    (head is Action) -> { // applicare l'azione a currentState e aggiornarlo"
                        val states = currentState.apply(head).toList()
                        if (states.isNotEmpty()) {
                            currentState = states.first()
                            choicePoints.update(states, stack, currentState, plan)
                            plan.add(head)
                        }
                        if ((plan.size > maxDepth || states.isEmpty()) && (backtrackOrFail())) return@sequence
                    }
                    else -> {
                        error("Handle the case where $head is ${head::class}(probably via backtracking)")
                    }
                }
            }
            yield(Plan.of(plan))
            if (backtrackOrFail()) return@sequence
        }
    }.distinct()
}
