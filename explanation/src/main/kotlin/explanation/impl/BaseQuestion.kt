package explanation.impl

import core.* // ktlint-disable no-wildcard-imports
import explanation.Question

/**
 *
 */
abstract class BaseQuestion : Question {
    /**
     *
     */
    protected abstract val newPredicate: Predicate

    /**
     *
     */
    protected abstract val newGroundFluent: Fluent

    /**
     *
     */
    protected abstract val newFluent: Fluent

    /**
     *
     */
    protected abstract val oldAction: Action

    /**
     *
     */
    protected abstract val newAction: Action

    /**
     *
     */
    protected abstract val hDomain: Domain

    /**
     *
     */
    @Suppress("SpreadOperator")
    fun createNewGroundFluent(operator: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *operator.args.toTypedArray())

    /**
     *
     */
    @Suppress("SpreadOperator")
    fun createNewFluent(operator: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *operator.parameters.keys.toTypedArray())

    /**
     *
     */
    fun createNewPredicate(action: Action, name: String): Predicate =
        Predicate.of(name + action.name, action.parameters.values.toList())

    /**
     *
     */
    fun createNewAction(action: Action, fluent: Fluent, negated: Boolean = false): Action {
        return Action.of(
            name = action.name + "^",
            parameters = action.parameters,
            preconditions = action.preconditions,
            effects = when (negated) {
                true -> mutableSetOf(Effect.negative(fluent)).also { it.addAll(action.effects) }
                else -> mutableSetOf(Effect.of(fluent)).also { it.addAll(action.effects) }
            }
        )
    }

    /**
     *
     */
    fun buildHdomain(domain: Domain, newPredicate: Predicate, newAction: Action) =
        Domain.of(
            name = domain.name,
            predicates = mutableSetOf(newPredicate).also { it.addAll(domain.predicates) },
            actions = mutableSetOf(newAction).also {
                domain.actions.map { oldAction ->
                    if (oldAction.name != newAction.name.filter { char -> char.isLetter() }) {
                        it.add(oldAction) // se il nome è diverso lo aggiungo
                    }
                }
            },
            types = domain.types
        )

    /**
     *
     */
    fun buildHproblem(
        hDomain: Domain,
        problem: Problem,
        newFluent: Fluent?,
        state: State?,
        updateState: Boolean = false
    ) =
        Problem.of(
            domain = hDomain,
            objects = problem.objects,
            initialState = when (updateState) {
                true -> State.of(mutableSetOf(newFluent!!).also { it.addAll(problem.initialState.fluents) })
                else -> state ?: problem.initialState
            },
            goal = when (newFluent) {
                null -> problem.goal
                else -> FluentBasedGoal.of(
                    (problem.goal as FluentBasedGoal).targets.toMutableSet()
                        .also { it.add(newFluent) }
                )
            }
        )
}
