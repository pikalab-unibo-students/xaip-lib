package explanation.impl

import Action
import Domain
import Fluent
import FluentBasedGoal
import Operator
import Predicate
import Problem
import State

/**
 *
 */
open class BaseQuestion {
    // o li tengo così o li tengo abstract (rimettendo la classe come abstract),
    // ma se li metto come abstract poi tocca implementarli anche dove non mi servono;
    /**
     *
     */
    open lateinit var newPredicate: Predicate

    /**
     *
     */
    open lateinit var newGroundFluent: Fluent

    /**
     *
     */
    open lateinit var newFluent: Fluent

    /**
     *
     */
    open lateinit var oldAction: Action

    /**
     *
     */
    open lateinit var newAction: Action

    /**
     *
     */
    open lateinit var hDomain: Domain

    /**
     *
     */
    fun findAction(inputOperator: Operator, actionList: Iterable<Action>): Action =
        actionList.first { it.name == inputOperator.name }

    /**
     *
     */
    @Suppress("SpreadOperator")
    fun createNewGroundFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.args.toTypedArray())

    /**
     *
     */
    @Suppress("SpreadOperator")
    fun createNewFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.parameters.keys.toTypedArray())

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
