package explanation.utils

import core.* // ktlint-disable no-wildcard-imports

/**
 *
 */

internal fun findAction(inputOperator: Operator, actionList: Iterable<Action>): Action =
    actionList.first { it.name == inputOperator.name }

/**
 *
 */
private fun Set<Fluent>.conditionMatch(conditions: Set<Effect>) =
    this.all { fluent1 ->
        conditions.any { effect ->
            effect.fluent.match(fluent1)
        }
    }

/**
 *
 */
internal fun Operator.isIdempotentOperators(operator: Operator): Boolean =
    this.preconditions.conditionMatch(operator.effects) &&
        operator.preconditions.conditionMatch(this.effects) &&
        this.args.all { operator.args.contains(it) }

/**
 *
 */
fun List<Operator>.retrieveArtificialOperator() =
    this.filter { it.name.contains("^") }.getOrNull(0)

/**
 *
 */
fun Set<Action>.retrieveAction(operator: Operator) =
    this.first {
        it.name == operator.name.filter { char -> char.isLetter() }
    }

/**
 *
 */
private fun createInitialOperator(action: Action, operator: Operator): Operator {
    var newOperator = Operator.of(action)
    for (arg in operator.args) {
        newOperator = newOperator.apply(
            VariableAssignment.of(
                operator.parameters.keys.toList()[operator.args.indexOf(arg)],
                arg
            )
        )
    }
    return newOperator
}

/**
 *
 */
fun List<Operator>.replaceArtificialOperator(actionList: Set<Action>): List<Operator> {
    val newList = mutableListOf<Operator>()
    this.toMutableList().map { operator ->
        if ("^" in operator.name) {
            newList.add(createInitialOperator(actionList.retrieveAction(operator), operator))
        } else newList.add(operator)
    }
    return newList
}

/**
 * */
fun buildIdempotendMinimalOperatorsRequiredList(
    minimalListOperators: List<Operator>,
    actions: Set<Action>,
    operatorsInPlan: List<Operator>
): MutableMap<Operator, IdempotentOperator> {
    // lista degli operatori che corrispondono ad un'azione necessaria
    val operatorsInPlanFiltered = mutableListOf<Operator>()
    // mappa che contiene gli operatori + le loro occorrenze + eventuali operatori idempotenti
    val idempotentOperatorsOccurences =
        mutableMapOf<Operator, IdempotentOperator>()
    // trovo le azioni minime che mi servono per avere un piano che funzioni
    val actionsRequired =
        minimalListOperators.map { findAction(it, actions) }
    // ciclo che mette gli operator necessari nella mappa.
    operatorsInPlan.map { operatorToEvaluate ->
        // trovo l'azione corrispondente all'operatore che sto valutando
        val actionToEvaluate =
            findAction(operatorToEvaluate, actions)
        // se questa azione è tra quelle irrinunciabili per l'accettazione del piano
        if (actionsRequired.contains(actionToEvaluate)) {
            // se l'operatore è già nella lista di quelli necessari
            // aggiorno il contatore corrispondente
            if (operatorsInPlanFiltered.contains(operatorToEvaluate)) {
                idempotentOperatorsOccurences[operatorToEvaluate]!!.occurence1++
            } else { // se non c'è lo aggiungo
                operatorsInPlanFiltered.add(operatorToEvaluate)
                idempotentOperatorsOccurences[operatorToEvaluate] = IdempotentOperator(1)
            }
        }
    }
    // ciclo che mette gli operator idempotenti nella mappa.
    operatorsInPlan.map { operatorToEvaluate ->
        // trovo l'azione corrispondente all'operatore che sto valutando
        val actionToEvaluate =
            findAction(operatorToEvaluate, actions)
        // se l'operatore non è tra quelli irrinunciabili potrebbe essere
        // uno di quelle idempotenti rispetto a uno che lo è.
        if (!actionsRequired.contains(actionToEvaluate)) {
            operatorsInPlanFiltered.map { operatorInList ->
                if (operatorInList.isIdempotentOperators(operatorToEvaluate)) {
                    if (idempotentOperatorsOccurences.containsKey(operatorInList)) {
                        idempotentOperatorsOccurences[operatorInList]!!.occurence2++
                        // sta roba potrebbe essere migliorata evitando la sovrascrizione ad ogni giro
                        idempotentOperatorsOccurences[operatorInList]!!.operator2 = operatorToEvaluate
                    }
                }
            }
        }
    }
    return idempotentOperatorsOccurences
}

/**
 * .
 * @property occurence1
 * @property occurence2
 * @property operator2
 * */
class IdempotentOperator(
    var occurence1: Int = 0,
    var operator2: Operator? = null,
    var occurence2: Int = 0
)
