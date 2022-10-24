package explanation.impl

import FluentBasedGoal
import Operator
import Plan
import explanation.Explainer
import explanation.Explanation
import explanation.Question
import explanation.Simulator
import explanation.utils.buildIdempotendMinimalOperatorsRequiredList
import explanation.utils.replaceOperator
import explanation.utils.retrieveOperator
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
        explainer.minimalPlanSelector()
    }
    private val operatorsMissing by lazy {
        minimalPlan.operators.filter { !question.plan.operators.contains(it) }
    }
    private val idempotentOperatorsWrongOccurrence by lazy {
        buildIdempotendMinimalOperatorsRequiredList(
            minimalPlan.operators,
            question.problem.domain.actions,
            novelPlan.operators
        ).filter { it.value.occurence1 <= it.value.occurence2 }
    }

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
            is QuestionAddOperator -> {
                var planCopy = question.plan.operators.toMutableList()
                planCopy.add(question.focusOn, question.focus)
                println("plan copy: $planCopy")
                val operator = planCopy.retrieveOperator()
                println("operator: $operator")
                if (operator != null) novelPlan =
                    Plan.of(planCopy.replaceOperator(question.problem.domain.actions))
                else
                     novelPlan = Plan.of(planCopy)
            }
            is QuestionRemoveOperator -> {
                novelPlan = explainer.planner.plan(question.buildHypotheticalProblem().first()).first()
                val operator = novelPlan.operators.retrieveOperator()
                if (operator != null) novelPlan =
                    Plan.of(novelPlan.operators.replaceOperator(question.problem.domain.actions))
            }
            is QuestionPlanProposal -> novelPlan = question.alternativePlan
            is QuestionPlanSatisfiability -> novelPlan = question.plan
        }
    }

    override fun isPlanValid(): Boolean {
        val states = simulator.simulate(novelPlan, question.problem.initialState)
        return (
            states.isNotEmpty() then
                states.all { finalStateComplaintWithGoal(question.problem.goal as FluentBasedGoal, it) }
            ) ?: false
    }

    /**
     * */
    override fun isPlanLengthAcceptable(): Boolean =
        minimalPlan.operators.size <= novelPlan.operators.size

    /**
     * */
    override fun isProblemSolvable(): Boolean =
        minimalPlan.operators.isNotEmpty()

    override fun minimalSolutionLength(): Int = minimalPlan.operators.size

    // TODO(Sta roba fatta così fa schifo)
    //  è logicamente sbagliata perché teoricamente filtro solo
    // le azioni idempotenti che mi invalidano il piano quindi sto nome non ci sta)
    override fun areIdempotentOperatorsPresent() = idempotentOperatorsWrongOccurrence

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
