package explanation.impl

import FluentBasedGoal
import explanation.Explanation

class ContrastiveExplanationPresenter(override val explanation: Explanation) : AbstractExplanationPresenter(explanation) {
    private val operatorsMissing by lazy {
        explanation.explainer.minimalPlanSelector().operators
            .filter { !explanation.question.plan.operators.contains(it) }
    }
    fun contrastiveExplanation(): String {
        if (explanation.isPlanValid()) {
            return """${ContrastiveExplanationPresenter::class.simpleName}(
            |  ${ExplanationImpl::originalPlan.name}=${explanation.originalPlan},
            |  ${ExplanationImpl::novelPlan.name}=${explanation.novelPlan},
            |   It has $additionalOperators additional operators,
            |  — Diff(original plan VS new plan):
            |  ${ExplanationImpl::addList.name}=${explanation.addList},
            |  ${ExplanationImpl::deleteList.name}=${explanation.deleteList},
            |  ${ExplanationImpl::existingList.name}=${explanation.existingList}
            |)
            """.trimMargin()
        } else {
            return """${ContrastiveExplanationPresenter::class.simpleName}(
                | the problem: ${(explanation.question.problem.goal as FluentBasedGoal)
                .targets} is solvable: ${explanation.isProblemSolvable()}
                | the plan: ${explanation.originalPlan.operators} is valid: ${explanation.isPlanValid()}
                | plan length acceptable: ${explanation.isPlanLengthAcceptable()}
                | operators missing: $operatorsMissing
                | idempotent operators causing errors: ${explanation.areIdempotentOperatorsPresent().entries.map {
                it.value.operator2
            }}
            """.trimIndent()
        }
    }
}
