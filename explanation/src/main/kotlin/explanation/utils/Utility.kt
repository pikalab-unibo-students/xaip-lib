package explanation.utils

import Action
import Effect
import Fluent
import Operator

internal fun findAction(inputOperator: Operator, actionList: Iterable<Action>): Action =
    actionList.first { it.name == inputOperator.name }

private fun Set<Fluent>.conditionMatch(conditions: Set<Effect>) =
    this.all { fluent1 ->
        conditions.any { effect ->
            effect.fluent.match(fluent1)
        }
    }

internal fun Operator.isIdempotentOperators(operator: Operator): Boolean =
    this.preconditions.conditionMatch(operator.effects) &&
        operator.preconditions.conditionMatch(this.effects) &&
        this.args.all { operator.args.contains(it) }
