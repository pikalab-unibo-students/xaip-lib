package explanation.impl

import core.Effect
import core.Fluent
import core.Operator
import core.Planner
import explanation.Explanation
import core.impl.res.FrameworkUtilities.then

class ExplanationExtended(val explanation: Explanation) {
    val minimalPlan by lazy { Planner.strips().plan(explanation.question.problem).first() }
    val problemSolvable by lazy { isProblemSolvable() }
    val planLenghtAcceptable by lazy { (isPlanLengthAcceptable() then isPlanLengthAcceptable()) ?: false }
    val operatorsMissing by lazy { minimalPlan.operators.filter { !explanation.question.plan.operators.contains(it) } }
    var idempotentActionList = mutableMapOf<Operator, IdempotentOperator>()

    fun isPlanLengthAcceptable(): Boolean =
        minimalPlan.operators.size < explanation.novelPlan.operators.size

    fun isProblemSolvable(): Boolean =
        minimalPlan.operators.isNotEmpty()

    fun Set<Fluent>.conditionMatch(conditions: Set<Effect>) = this.all { fluent1 ->
        conditions.any { effect ->
            effect.fluent.match(fluent1)
        }
    }

    fun isIdempotentActions(operator1: Operator, operator2: Operator): Boolean = operator1.preconditions.conditionMatch(operator2.effects) &&
        operator2.preconditions.conditionMatch(operator1.effects) &&
        operator1.args.all { operator2.args.contains(it) }

    class IdempotentOperator(
        var occurence1: Int = 0,
        var operator2: Operator? = null,
        var occurence2: Int = 0
    )

    fun idempotentList(): MutableMap<Operator, IdempotentOperator> {
        // inizializzola listo con gli operatori che devo avere perché il piano possa essere ritenuto valido
        for (operator in minimalPlan.operators) {
            idempotentActionList[operator] = IdempotentOperator()
        }
        // vado a controllare gli operatori nel piano che mi passa l'utonto
        for (operatorInUserPlan in explanation.novelPlan.operators) {
            // se l'operator non c'è allora devo controllare se è idempotente rispetto a qualche operator
            // essenziale per l'accettazione del piano
            if (!idempotentActionList.contains(operatorInUserPlan)) {
                for (essentialOperator in idempotentActionList) {
                    if (isIdempotentActions(essentialOperator.key, operatorInUserPlan)) {
                        // potrei evitare di sovrascrivere tutte le volte ma per ora va bene così
                        idempotentActionList[essentialOperator.key]?.operator2 = operatorInUserPlan
                        idempotentActionList[essentialOperator.key]?.occurence2?.inc()
                    }
                }
            } // se invece contiene l'elemento aggiorno il contatore
            idempotentActionList[operatorInUserPlan]?.occurence1 = idempotentActionList[operatorInUserPlan]?.occurence1!! + 1
        }
        // filtro mantenendo solo gli operatori che mi invalidano il piano
        idempotentActionList = idempotentActionList
            .filter { it.value.occurence1 <= it.value.occurence2 } as MutableMap<Operator, IdempotentOperator>
        return idempotentActionList
    }
}
