package impl

import Action
import Applicable
import Effect
import Fluent
import FluentBasedGoal
import State
import VariableAssignment
import java.util.*

internal data class ExecutionContext(
    var currentState: State,
    var stack: Stack<Applicable<*>>,
    var maxDepth: Int,
    var choicePoints: Deque<ChoicePoint> = LinkedList(),
    var plan: MutableList<Action> = mutableListOf()
) {

    constructor(
        currentState: State,
        goal: FluentBasedGoal,
        maxDepth: Int
    ) : this(currentState, Stack<Applicable<*>>().also { it.add(goal) }, maxDepth)

    private fun Stack<Applicable<*>>.apply(substitution: VariableAssignment) {
        val iterator = listIterator()
        while (iterator.hasNext()) {
            val x = iterator.next()
            iterator.set(x.apply(substitution))
        }
    }

    private fun Set<Action>.`actions whose effects match head`(head: Effect) =
        map { it.refresh() }.filter { action ->
            action.positiveEffects.any { effect ->
                effect.match(head)
            }
        }

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

    private fun Deque<ChoicePoint>.update(
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
