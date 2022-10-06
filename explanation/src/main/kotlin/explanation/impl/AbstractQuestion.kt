package explanation.impl

import Action
import Domain
import Fluent
import FluentBasedGoal
import Operator
import Plan
import Predicate
import Problem
import State

/**
 *
 */
open class AbstractQuestion {
    // o li tengo così o li tengo abstract (rimettendo la classe come abstract),
    // ma se li metto come abstract poi tocca implementarli anche dove non mi servono;
    // vedi in Q3

    open lateinit var newPredicate: Predicate
    open lateinit var newGroundFluent: Fluent
    open lateinit var newFluent: Fluent
    open lateinit var oldAction: Action
    open lateinit var newAction: Action
    open lateinit var hDomain: Domain

    /**
     *
     */
    fun reorderPlan(
        plan: Plan,
        actionsToPosticipate: List<Operator>,
        actionsToAnticipate: List<Operator>
    ): List<Operator> {
        val indiceInizio = plan.actions.indexOf(actionsToPosticipate.first())
        val indiceFine = plan.actions.indexOf(actionsToPosticipate.last())
        val indiceInizio2 = plan.actions.indexOf(actionsToAnticipate.last())
        val indiceFine2 = plan.actions.indexOf(actionsToAnticipate.last())

        val reorderedPlan = plan.actions.subList(0, indiceInizio).toMutableList()
            .also { it.addAll(actionsToAnticipate) }
            .also {
                it.addAll(
                    plan.actions.subList(
                        indiceFine + 1,
                        indiceInizio2
                    )
                )
            }
            .also { it.addAll(actionsToPosticipate) }
            .also {
                it.addAll(
                    plan.actions.subList(
                        indiceFine2 + 1,
                        plan.actions.size
                    )
                )
            }
        return reorderedPlan
    }

    /**
     *
     */
    fun findAction(inputOperator: Operator, actionList: Iterable<Action>): Action =
        actionList.first { it.name == inputOperator.name }

    /**
     *
     */
    fun createNewGroundFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.args.toTypedArray())

    /**
     *
     */
    fun createNewFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.parameters.keys.toTypedArray())

    /**
     *
     */
    fun createNewPredicate(action: Action, name: String, negated: Boolean = false): Predicate =
        if (negated) Predicate.of(name + action.name, action.parameters.values.toList())
        else Predicate.of(name + action.name, action.parameters.values.toList())

    /**
     *
     */
    fun createNewAction(action: Action, fluent: Fluent, negated: Boolean = false): Action {
        return Action.of(
            name = action.name + "^",
            parameters = action.parameters,
            preconditions = action.preconditions,
            effects = if (negated) mutableSetOf(Effect.negative(fluent)).also {
                it.addAll(action.effects)
            } else mutableSetOf(Effect.of(fluent)).also { it.addAll(action.effects) }
        )
    }

    /**
     *
     */
    fun buildHdomain(domain: Domain, newPredicate: Predicate, newAction: Action) =
        Domain.of( // domain extended
            name = domain.name,
            predicates = mutableSetOf(newPredicate).also { it.addAll(domain.predicates) },
            actions = mutableSetOf(newAction).also {
                domain.actions.map { oldAction ->
                    if (oldAction.name != newAction.name.filter { char ->
                        char.isLetter()
                    } // se il nome è diverso lo aggiungo
                    ) it.add(oldAction)
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
        Problem.of( // problem extended
            domain = hDomain,
            objects = problem.objects,
            initialState =
            if (updateState) State.of(
                mutableSetOf(newFluent!!).also {
                    it.addAll(problem.initialState.fluents)
                }
            ) else state ?: problem.initialState, // extended
            goal = if (newFluent != null) {
                FluentBasedGoal.of(
                    (problem.goal as FluentBasedGoal).targets.toMutableSet().also {
                        it.add(newFluent)
                    }
                )
            } else problem.goal
        )
}
