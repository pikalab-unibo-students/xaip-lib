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

    private data class ChoicePoint(val stack: Stack<Applicable<*>>, val state: State, val plan: MutableList<Action>)

    private data class ExecutionContext(
        var currentState: State,
        var stack: Stack<Applicable<*>>,
        var choicePoints: Deque<ChoicePoint>,
        var plan: MutableList<Action>,
        var maxDepth: Int
    ) {
        private fun Stack<Applicable<*>>.apply(substitution: VariableAssignment) {
            val iterator = listIterator()
            while (iterator.hasNext()) {
                val x = iterator.next()
                iterator.set(x.apply(substitution))
            }
        }

        private fun Set<Action>.`actions whose effects match head`(head: Effect) =
            map { it.refresh() }.filter { action -> action.positiveEffects.any { effect -> effect.match(head) } }

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

        fun handleAction(head: Action): Boolean {
            val states = currentState.apply(head).toList()
            if (states.isNotEmpty()) {
                currentState = states.first()
                choicePoints.update(states, stack, currentState, plan)
                plan.add(head)
            }
            return (plan.size > maxDepth || states.isEmpty()) && backtrackOrFail()
        }

        fun handleFluentInCurrentState(head: Fluent) {
            val substitutions = currentState.fluents.filter { it.match(head) }.map { it.mostGeneralUnifier(head) }
            choicePoints.update(substitutions, stack, currentState, plan)
            stack.update(substitutions.first())
        }

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

        fun handleFluentNotInCurrentState(
            head: Fluent,
            actions: Set<Action>
        ) {
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

    @Suppress("UNCHECKED_CAST")
    private fun plan(
        initialState: State,
        actions: Set<Action>,
        goal: FluentBasedGoal,
        maxDepth: Int
    ): Sequence<Plan> = sequence<Plan> {
        val context = ExecutionContext(
            initialState,
            Stack<Applicable<*>>().also { it.push(goal) },
            LinkedList(),
            mutableListOf(),
            maxDepth
        )

        while (true) {
            while (context.stack.isNotEmpty()) {
                val head = context.stack.pop()
                when {
                    head is Fluent -> { // "applica la sostituzione a tutto lo stack"
                        if (context.currentState.fluents.any { it.match(head) }) {
                            context.handleFluentInCurrentState(head)
                        } else { // "retrieve dell'azione - push dell'azione -push delle precondizioni dell'azione"
                            context.handleFluentNotInCurrentState(head, actions)
                        }
                    }
                    (head is FluentBasedGoal) -> {
                        context.stack.addAll(head.targets)
                    }
                    (head is Action) -> { // applicare l'azione a currentState e aggiornarlo"
                        if (context.handleAction(head)) return@sequence
                    }
                    else -> {
                        error("Handle the case where $head is ${head::class}(probably via backtracking)")
                    }
                }
            }
            yield(Plan.of(context.plan))
            if (context.backtrackOrFail()) return@sequence
        }
    }.distinct()
}
