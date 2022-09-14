package resources

import Action
import Fluent
import Operator
import Plan
import Predicate
import Problem

object ExplanationUtils {
    data class Question1(val actionToAdd: Operator, val problem: Problem, val plan: Plan)
    data class ContrastiveExplanation(
        val addList: Set<Operator>,
        val deleteList: Set<Operator>,
        val existingList: Set<Operator>
    )

    fun buildExplanation(plan: Plan, Hplan: Plan) {
        val addList: MutableSet<Operator> = mutableSetOf()
        val deleteList: MutableSet<Operator> = mutableSetOf()
        val existingList: MutableSet<Operator> = mutableSetOf()

        plan.actions.map {
            if (!Hplan.actions.contains(it)) deleteList.add(it as Operator)
            if (Hplan.actions.contains(it)) existingList.add(it as Operator)
        }

        Hplan.actions.map {
            if (!plan.actions.contains(it)) addList.add(it as Operator)
        }

        val explanation = ContrastiveExplanation(addList, deleteList, existingList)
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
            effects = if (negated) mutableSetOf(Effect.negative(fluent)).also { it.addAll(action.effects) } else mutableSetOf(Effect.of(fluent)).also { it.addAll(action.effects) }
        )
    }

    fun findAction(inputOperator: Operator, actionList: Iterable<Action>): Action =
        actionList.first { it.name == inputOperator.name }
}
