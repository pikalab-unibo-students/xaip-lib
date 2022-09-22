package resources

import Action
import Domain
import Fluent
import FluentBasedGoal
import Operator
import Plan
import Predicate
import Problem
import State

object ExplanationUtils {
    data class Question1(val actionToAddOrToRemove: Operator, val problem: Problem, val originalPlan: Plan) {
        override fun toString(): String =
            """${Question1::class.simpleName}(
            |  ${Question1::actionToAddOrToRemove.name}=$actionToAddOrToRemove,
            |  ${Question1::problem.name}=$problem,
            |  ${Question1::originalPlan.name}=$originalPlan,
            |)
            """.trimMargin()
    }

    interface ContrastiveExplanation {
        val originalPlan: Plan
        val newPlan: Plan
        val actionToAddOrRemove: Operator
        val addList: Set<Operator>
        val deleteList: Set<Operator>
        val existingList: Set<Operator>

        companion object {
            /**
             * Factory method for an [ContrastiveExplanation] creation.
             */
            fun of(
                originalPlan: Plan,
                newPlan: Plan,
                actionToAddOrRemove: Operator
            ): ContrastiveExplanation = ContrastiveExplanationImpl(originalPlan, newPlan, actionToAddOrRemove)
        }
    }

    data class ContrastiveExplanationImpl(
        override val originalPlan: Plan,
        override val newPlan: Plan,
        override val actionToAddOrRemove: Operator
    ) : ContrastiveExplanation {
        override val addList: Set<Operator> by lazy {
            newPlan.actions.filter {
                !originalPlan.actions.contains(it)
            }.map { it }.toSet()
        }
        override val deleteList: Set<Operator> by lazy {
            originalPlan.actions.filter {
                !newPlan.actions.contains(it)
            }.map { it }.toSet()
        }
        override val existingList: Set<Operator> by lazy {
            originalPlan.actions.filter {
                newPlan.actions.contains(it)
            }.map { it }.toSet()
        }

        override fun toString(): String =
            """${ContrastiveExplanation::class.simpleName}(
            |  ${ContrastiveExplanation::originalPlan.name}=$originalPlan,
            |  ${ContrastiveExplanation::newPlan.name}=$newPlan,
            |  ${ContrastiveExplanation::actionToAddOrRemove.name}=$actionToAddOrRemove,
            |  - Diff(original plan VS new plan):
            |  ${ContrastiveExplanation::addList.name}=$addList,
            |  ${ContrastiveExplanation::deleteList.name}=$deleteList,
            |  ${ContrastiveExplanation::existingList.name}=$existingList
            |)
            """.trimMargin()

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ContrastiveExplanation

            if (originalPlan != other.originalPlan) return false
            if (newPlan != other.newPlan) return false
            if (actionToAddOrRemove != other.actionToAddOrRemove) return false

            return true
        }

        override fun hashCode(): Int {
            var result = originalPlan.hashCode()
            result = 31 * result + newPlan.hashCode()
            result = 31 * result + actionToAddOrRemove.hashCode()
            return result
        }
    }

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

    // extended
    fun createNewGroundFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.args.toTypedArray())

    fun createNewFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.parameters.keys.toTypedArray())

    fun createNewPredicate(action: Action, name: String, negated: Boolean = false): Predicate =
        if (negated) Predicate.of(name + action.name, action.parameters.values.toList())
        else Predicate.of(name + action.name, action.parameters.values.toList())

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

    fun findAction(inputOperator: Operator, actionList: Iterable<Action>): Action =
        actionList.first { it.name == inputOperator.name }
}
