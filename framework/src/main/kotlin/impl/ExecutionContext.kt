package impl

import Action
import Applicable
import Effect
import Fluent
import FluentBasedGoal
import NotUnifiableException
import Operator
import State
import VariableAssignment
import it.unibo.tuprolog.core.Substitution
import java.util.*

internal data class ExecutionContext(
    var currentState: State,
    var stack: Stack<Applicable<*>>,
    val maxDepth: Int,
    var choicePoints: Deque<ChoicePoint> = LinkedList(),
    var plan: MutableList<Operator> = mutableListOf()
) {

    constructor(
        currentState: State,
        goal: FluentBasedGoal,
        maxDepth: Int
    ) : this(currentState, Stack<Applicable<*>>().also { it.add(goal) }, maxDepth)

    val depth: Int
        get() = stack.filterIsInstance<Action>().count()

    private fun Stack<Applicable<*>>.apply(substitution: VariableAssignment) {
        val iterator = listIterator()
        while (iterator.hasNext()) {
            val x = iterator.next()
            iterator.set(x.apply(substitution))
        }
    }

    private fun Iterable<Operator>.actionsWhoseEffectsMatchHead(head: Effect) =
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
            is Operator -> {
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
        plan: List<Operator>,
        effect: Effect? = null
    ) {
        for (elem in changes.asSequence().drop(1)) {
            @Suppress("UNCHECKED_CAST")
            val stackCopy = stack.clone() as Stack<Applicable<*>>
            when (elem) {
                is VariableAssignment -> {
                    stackCopy.apply(elem)
                }
                is Operator -> {
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

    fun handleAction(head: Operator): Boolean {
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
        while (choicePoints.isNotEmpty()) {
            val choicePoint = choicePoints.pollFirst()
            stack = choicePoint.stack
            currentState = choicePoint.state
            plan = choicePoint.plan
            if (depth <= maxDepth) {
                return false
            }
        }
        return true
    }

    fun handleFluentNotInCurrentState(
        head: Fluent,
        actions: Set<Action>
    ): Boolean {
        val h = Effect.of(head)
        val actionsMatched = actions.map { Operator.of(it) }
            .actionsWhoseEffectsMatchHead(h)
            .toMutableList()

        if (actionsMatched.isEmpty()) return backtrackOrFail()

        val action = actionsMatched.first()
        choicePoints.update(actionsMatched, stack, currentState, plan)
        stack.update(action, h)

        val effectsMatched = action.positiveEffects.filter { effect -> effect.match(h) }
        choicePoints.update(effectsMatched, stack, currentState, plan, h)
        stack.update(effectsMatched.first(), h)

        return depth > maxDepth && backtrackOrFail()
    }

    fun finalStateComplaintWithGoal(goal: FluentBasedGoal, currentState: State): Boolean {
        var indice = 0
        for (fluent in goal.targets) {
            if (!fluent.isGround) {
                for (fluentState in currentState.fluents) {
                    val tmp = try {
                        fluentState.mostGeneralUnifier(fluent)
                    } catch (_: NotUnifiableException) { null }
                    if (tmp != Substitution.empty() || tmp != Substitution.empty()) {
                        indice++
                        break
                    }
                }
            } else {
                if (currentState.fluents.contains(fluent)) indice++ else indice--
            }
        }
        return goal.targets.size == indice
    }

    override fun toString(): String =
        """${ExecutionContext::class.simpleName}(
            |  ${ExecutionContext::currentState.name}=$currentState,
            |  ${ExecutionContext::currentState.name}.hash=${currentState.hashCode()},
            |  ${ExecutionContext::stack.name}=${stack.asReversed()},
            |  ${ExecutionContext::depth.name}=$depth/$maxDepth,
            |  ${ExecutionContext::plan.name}=$plan,
            |  ${ExecutionContext::choicePoints.name}=${choicePoints.toSet().size
        }
            |)
        """.trimMargin()
    // |  ${ExecutionContext::choicePoints.name}=${choicePoints.toString().replace("\n", "\n  ")}
    //
}
