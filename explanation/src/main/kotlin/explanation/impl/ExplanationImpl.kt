package explanation.impl

import Action
import Fluent
import FluentBasedGoal
import Operator
import Plan
import explanation.Explanation
import explanation.Question
import explanation.Simulator
import impl.res.FrameworkUtilities.finalStateComplaintWithGoal
import impl.res.FrameworkUtilities.then

/**
 *
 */
data class ExplanationImpl(
    override val originalPlan: Plan,
    override var novelPlan: Plan,
    override val question: Question
) : Explanation {
    override val addList: List<Operator> by lazy {
        this.novelPlan.operators.filter {
            !this.originalPlan.operators.contains(it)
        }
    }
    override val deleteList: List<Operator> by lazy {
        this.originalPlan.operators.filter {
            !this.novelPlan.operators.contains(it)
        }
    }
    override val existingList: List<Operator> by lazy {
        this.originalPlan.operators.filter {
            this.novelPlan.operators.contains(it)
        }
    }
    private val simulator = Simulator.of()

    private fun retrieveOperator() = novelPlan.operators.filter { it.name.contains("^") }.getOrNull(0)

    private fun retrieveAction() = novelPlan.operators.map { operator ->
        question.problem.domain.actions.first {
            it.name == operator.name.filter { char -> char.isLetter() } &&
                operator.name.contains("^")
        }
    }.first()

    private fun makeFinalOperator(action: Action, operator: Operator): Operator {
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

    private fun List<Operator>.replaceElement(element: Operator): List<Operator> =
        this.toMutableList()
            .subList(0, this.indexOf(element)).also {
                it.add(makeFinalOperator(retrieveAction(), element))
            }.also {
                it.addAll(
                    this.subList(this.indexOf(element) + 1, this.size)
                )
            }

    init {
        when (question) {
            is Question3 -> {
                val operatorsToKeep = question.plan.operators.subList(0, question.focusOn).toMutableList()
                novelPlan = Plan.of(
                    operatorsToKeep
                        .also { it.add(question.focus) }.also { it.addAll(novelPlan.operators) }
                )
            }
            is Question1, is Question2 -> {
                val operator = retrieveOperator()
                if (operator != null) novelPlan = Plan.of(novelPlan.operators.replaceElement(operator))
            }
        }
    }

    override fun isPlanValid(): Boolean {
        val states = simulator.simulate(novelPlan, question.problem.initialState)
        return (
            states.isNotEmpty() then
                states.all { finalStateComplaintWithGoal(question.problem.goal as FluentBasedGoal, it) }
            ) ?: false
    }

    override fun toString(): String =
        """${ExplanationImpl::class.simpleName}(
            |  ${ExplanationImpl::originalPlan.name}=${this.originalPlan},
            |  ${ExplanationImpl::novelPlan.name}=${this.novelPlan},
            |  the novel plan is valid: ${this.isPlanValid()},
            |  - Diff(original plan VS new plan):
            |  ${ExplanationImpl::addList.name}=$addList,
            |  ${ExplanationImpl::deleteList.name}=$deleteList,
            |  ${ExplanationImpl::existingList.name}=$existingList
            |)
        """.trimMargin()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExplanationImpl

        if (this.originalPlan != other.originalPlan) return false
        if (this.novelPlan != other.novelPlan) return false

        return true
    }

    // TODO(Ma è necessario l'override dell'hashcode)
    override fun hashCode(): Int {
        var result = this.originalPlan.hashCode()
        result = 31 * result + this.novelPlan.hashCode()
        return result
    }

    class explanationExtended(val question: Question) {
        val minimalPlan = Planner.strips().plan(question.problem).first()
        var problemSolvable = isProblemSolvable()
        var planLenghtAcceptable = (isPlanLengthAcceptable() then isPlanLengthAcceptable()) ?: false
        var operatorsMissing = minimalPlan.operators.filter { !question.plan.operators.contains(it) }
        var idempotentActionList = mutableMapOf<Operator, IdempotentOperator>()

        private fun isPlanLengthAcceptable(): Boolean =
            minimalPlan.operators.size > question.plan.operators.size

        private fun isProblemSolvable(): Boolean =
            minimalPlan.operators.isNotEmpty()

        private fun Set<Fluent>.conditionMatch(conditions: Set<Fluent>) = this.all { fluent1 ->
            conditions.any { fluent2 ->
                fluent2.match(fluent1)
            }
        }

        fun isIdempotentActions(operator1: Operator, operator2: Operator) =
            operator1.preconditions.conditionMatch(operator2.preconditions) &&
                operator2.preconditions.conditionMatch(operator1.preconditions) &&
                operator1.args.all { operator2.args.contains(it) }

        class IdempotentOperator {
            var occurence1 = 0
            var operator2: Operator? = null
            var occurence2 = 0
        }

        fun idempotentList() {
            // inizializzola listo con gli operatori che devo avere perché il piano possa essere ritenuto valido
            for (operator in minimalPlan.operators) {
                idempotentActionList[operator] = IdempotentOperator()
            }
            // vado a controllare gli operatori nel piano che mi passa l'utonto
            for (operatorInUserPlan in question.plan.operators) {
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
                else idempotentActionList[operatorInUserPlan]?.occurence1?.inc()
            }
            // filtro mantenendo solo gli operatori che mi invalidano il piano
            idempotentActionList = idempotentActionList
                .filter { it.value.occurence1 <= it.value.occurence2 } as MutableMap<Operator, IdempotentOperator>
        }
    }
}
