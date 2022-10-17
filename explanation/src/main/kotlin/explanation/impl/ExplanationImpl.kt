package explanation.impl

import Action
import FluentBasedGoal
import Operator
import Plan
import explanation.Explainer
import explanation.Explanation
import explanation.Question
import explanation.Simulator
import explanation.utils.findAction
import explanation.utils.isIdempotentOperators
import impl.res.FrameworkUtilities.finalStateComplaintWithGoal
import impl.res.FrameworkUtilities.then

/**
 *
 */
data class ExplanationImpl(
    override val question: Question,
    override val explainer: Explainer
) : Explanation {
    override var novelPlan: Plan = Plan.of(emptyList())

    override val originalPlan: Plan by lazy {
        this.question.plan
    }
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

    private val minimalPlan by lazy {
        explainer.minimalPlanSelector { explainer.planner.plan(question.problem).first() }
    }
    private val problemSolvable by lazy {
        isProblemSolvable()
    }
    private val planLenghtAcceptable by lazy {
        (isPlanLengthAcceptable() then isPlanLengthAcceptable()) ?: false
    }
    private val operatorsMissing by lazy {
        minimalPlan.operators.filter { !question.plan.operators.contains(it) }
    }
    private var idempotentAction = mutableMapOf<Operator, IdempotentOperator>()

    init {
        when (question) {
            is QuestionReplaceOperator -> {
                val operatorsToKeep = question.plan.operators.subList(0, question.focusOn).toMutableList()
                novelPlan = Plan.of(
                    operatorsToKeep
                        .also { it.add(question.insteadOf) }
                        .also {
                            it.addAll(
                                explainer.planner.plan(question.buildHypotheticalProblem().first()).first().operators
                            )
                        }
                )
            }
            is QuestionAddOperator, is QuestionRemoveOperator -> {
                novelPlan = explainer.planner.plan(question.buildHypotheticalProblem().first()).first()
                val operator = retrieveOperator()
                if (operator != null) novelPlan = Plan.of(novelPlan.operators.replaceElement(operator))
            }
            is QuestionPlanProposal -> novelPlan = question.alternativePlan
            is QuestionPlanSatisfiability -> novelPlan = question.alternativePlan
        }
    }

    private fun retrieveOperator() =
        novelPlan.operators.filter { it.name.contains("^") }.getOrNull(0)

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

    override fun isPlanValid(): Boolean {
        val states = simulator.simulate(novelPlan, question.problem.initialState)
        return (
            states.isNotEmpty() then
                states.all { finalStateComplaintWithGoal(question.problem.goal as FluentBasedGoal, it) }
            ) ?: false
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * */
    fun isPlanLengthAcceptable(): Boolean =
        minimalPlan.operators.size <= novelPlan.operators.size

    /**
     * */
    fun isProblemSolvable(): Boolean =
        minimalPlan.operators.isNotEmpty()

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
     * */
    fun idempotentList(): MutableMap<Operator, IdempotentOperator> {
        // lista degli operatori che corrispondono ad un'azione necessaria
        val operatorsInPlanFiltered = mutableListOf<Operator>()
        // mappa che contiene gli operatori + le loro occorrenze + eventuali operatori idempotenti
        val idempotentOperatorsOccurences =
            mutableMapOf<Operator, IdempotentOperator>()
        // trovo le azioni minime che mi servono per avere un piano che funzioni
        val actionsRequired =
            minimalPlan.operators.map { findAction(it, question.problem.domain.actions) }
        // ciclo che mette gli operator necessari nella mappa.
        novelPlan.operators.map { operatorToEvaluate ->
            // trovo l'azione corrispondente all'operatore che sto valutando
            val actionToEvaluate =
                findAction(operatorToEvaluate, question.problem.domain.actions)
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
        novelPlan.operators.map { operatorToEvaluate ->
            // trovo l'azione corrispondente all'operatore che sto valutando
            val actionToEvaluate =
                findAction(operatorToEvaluate, question.problem.domain.actions)
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

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
}
