package explanation.impl

import Action
import Effect
import Fluent
import Operator
import explanation.Explanation
import impl.res.FrameworkUtilities.then

/**
 * .
 * @property explanation
 */
class ExplanationExtended(val explanation: Explanation) {
    val minimalPlan by lazy {
        Planner.strips().plan(explanation.question.problem).first()
    }
    private val problemSolvable by lazy {
        isProblemSolvable()
    }
    private val planLenghtAcceptable by lazy {
        (isPlanLengthAcceptable() then isPlanLengthAcceptable()) ?: false
    }
    private val operatorsMissing by lazy {
        minimalPlan.operators.filter { !explanation.question.plan.operators.contains(it) }
    }
    private var idempotentAction = mutableMapOf<Operator, IdempotentOperator>()

    /**
     * */
    fun isPlanLengthAcceptable(): Boolean =
        minimalPlan.operators.size <= explanation.novelPlan.operators.size

    /**
     * */
    fun isProblemSolvable(): Boolean =
        minimalPlan.operators.isNotEmpty()

    private fun Set<Fluent>.conditionMatch(conditions: Set<Effect>) =
        this.all { fluent1 ->
            conditions.any { effect ->
                effect.fluent.match(fluent1)
            }
        }

    /**
     * */
    fun isIdempotentActions(operator1: Operator, operator2: Operator): Boolean =
        operator1.preconditions.conditionMatch(operator2.effects) &&
            operator2.preconditions.conditionMatch(operator1.effects) &&
            operator1.args.all { operator2.args.contains(it) }

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

    /**
     *
     */
    fun findAction(inputOperator: Operator, actionList: Iterable<Action>): Action =
        actionList.first { it.name == inputOperator.name }

    /**
     * */
    fun idempotentList(): MutableMap<Operator, IdempotentOperator> {
        // lista degli operatori che corrispondono ad un'azione necessaria
        val operatorsInPlanFiltered = mutableListOf<Operator>()
        // mappa che contiene gli operatori + le loro occorrenze + eventuali operatori idempotenti
        val idempotentOperatorsOccurences =
            mutableMapOf<Operator, IdempotentOperator>()
        // trovo le azioni minime che mi servono per avere un piano che funzioni
        val actionsRequired =
            minimalPlan.operators.map { findAction(it, explanation.question.problem.domain.actions) }
        // ciclo che mette gli operator necessari nella mappa.
        explanation.novelPlan.operators.map { operatorToEvaluate ->
            // trovo l'azione corrispondente all'operatore che sto valutando
            val actionToEvaluate =
                findAction(operatorToEvaluate, explanation.question.problem.domain.actions)
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
        explanation.novelPlan.operators.map { operatorToEvaluate ->
            // trovo l'azione corrispondente all'operatore che sto valutando
            val actionToEvaluate =
                findAction(operatorToEvaluate, explanation.question.problem.domain.actions)
            // se l'operatore non è tra quelli irrinunciabili potrebbe essere
            // uno di quelle idempotenti rispetto a uno che lo è.
            if (!actionsRequired.contains(actionToEvaluate)) {
                operatorsInPlanFiltered.map { operatorInList ->
                    if (isIdempotentActions(operatorInList, operatorToEvaluate)) {
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
}
