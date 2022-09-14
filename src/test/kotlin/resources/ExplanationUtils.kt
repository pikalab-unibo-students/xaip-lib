package resources

import Action
import Fluent
import Operator
import Plan
import Predicate
import Problem

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
    data class ContrastiveExplanation(
        val addList: Set<Operator>,
        val deleteList: Set<Operator>,
        val existingList: Set<Operator>,
        val originalPlan: Plan,
        val newPlan: Plan,
        val actionToAddOrRemove: Operator
    ) {
        override fun toString(): String =
            """${ContrastiveExplanation::class.simpleName}(
            |  ${ContrastiveExplanation::originalPlan.name}=$originalPlan,
            |  ${ContrastiveExplanation::newPlan.name}=$newPlan,
            |  ${ContrastiveExplanation::actionToAddOrRemove.name}=$actionToAddOrRemove,
            |  -------------------------------------------------------------------------
            |  Diff(original plan VS new plan):
            |  ${ContrastiveExplanation::addList.name}=$addList,
            |  ${ContrastiveExplanation::deleteList.name}=$deleteList,
            |  ${ContrastiveExplanation::existingList.name}=$existingList
            |)
            """.trimMargin()
    }

    fun buildExplanation(plan: Plan, hPlan: Plan, action: Operator) {
        val addList: MutableSet<Operator> = mutableSetOf()
        val deleteList: MutableSet<Operator> = mutableSetOf()
        val existingList: MutableSet<Operator> = mutableSetOf()

        plan.actions.map {
            if (!hPlan.actions.contains(it)) deleteList.add(it as Operator)
            if (hPlan.actions.contains(it)) existingList.add(it as Operator)
        }

        hPlan.actions.map {
            if (!plan.actions.contains(it)) addList.add(it as Operator)
        }

        val explanation = ContrastiveExplanation(addList, deleteList, existingList, plan, hPlan, action)
        println(explanation)
    }

    fun createNewFluent(action: Operator, predicate: Predicate): Fluent =
        Fluent.positive(predicate, *action.args.toTypedArray().reversedArray())

    fun newPredicate(action: Action, negated: Boolean = false): Predicate =
        if (negated) Predicate.of("not_has_done_" + action.name, action.parameters.values.toList())
        else Predicate.of("has_done_" + action.name, action.parameters.values.toList())

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
