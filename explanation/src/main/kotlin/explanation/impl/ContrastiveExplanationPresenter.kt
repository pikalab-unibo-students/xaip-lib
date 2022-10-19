package explanation.impl

import FluentBasedGoal
import explanation.Explanation
import explanation.ExplanationPresenter

class ContrastiveExplanationPresenter(override val explanation: Explanation) : AbstractExplanationPresenter(explanation) {
    fun contrastiveExplanation(): String {
        if (explanation.isPlanValid()) {
            return """${ContrastiveExplanationPresenter::class.simpleName}(
            |  ${ExplanationImpl::originalPlan.name}=${explanation.originalPlan},
            |  ${ExplanationImpl::novelPlan.name}=${explanation.novelPlan},
            |   It has $additionalOperators additional operators,
            |  - Diff(original plan VS new plan):
            |  ${ExplanationImpl::addList.name}=${explanation.addList},
            |  ${ExplanationImpl::deleteList.name}=${explanation.deleteList},
            |  ${ExplanationImpl::existingList.name}=${explanation.existingList}
            |)
            """.trimMargin()
        } else {
            return """${ExplanationImpl::explanation.simpleName}(
                | the problem: ${(explanation.question.problem.goal as FluentBasedGoal)
                .targets} is solvable: ${isProblemSolvable()}
                | the plan: ${this.originalPlan.operators} is valid: ${isPlanValid()}
                | plan length acceptable: ${isPlanLengthAcceptable()}
                | operators missing: ${this.operatorsMissing}
                | idempotent operators causing errors: ${explanation.idempotentOperatorsWrongOccurrence.entries.map {
                it.value.operator2
            }}
            """.trimIndent()
        }
    }
}
