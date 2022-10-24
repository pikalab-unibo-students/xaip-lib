package explanation.impl

import core.FluentBasedGoal
import explanation.Explanation

class ContrastiveExplanationPresenter(explanation: Explanation) :
    BaseExplanationPresenter(explanation) {
    private val operatorsMissing by lazy {
        explanation.explainer.minimalPlanSelector().operators
            .filter { !explanation.novelPlan.operators.contains(it) }
    }

    /**
     *
     */
    fun presentContrastiveExplanation(): String {
        if (explanation.isPlanValid()) {
            return """${ContrastiveExplanationPresenter::class.simpleName}(
                | the problem: ${(explanation.question.problem.goal as FluentBasedGoal)
                .targets} is solvable: ${explanation.isProblemSolvable()}
                |  the original plan was: ${explanation.originalPlan.operators},
                |  the new plan is: ${explanation.novelPlan.operators},
                |  — Diff(original plan VS new plan):
                |  ${ExplanationImpl::addList.name}=${explanation.addList},
                |  ${ExplanationImpl::deleteList.name}=${explanation.deleteList},
                |  ${ExplanationImpl::existingList.name}=${explanation.existingList}
                |
            )
            """.trimMargin()
        } else {
            return """${ContrastiveExplanationPresenter::class.simpleName}(
                | the problem: ${(explanation.question.problem.goal as FluentBasedGoal)
                .targets} is solvable: ${explanation.isProblemSolvable()}
                | the plan: ${explanation.novelPlan.operators} is valid: ${explanation.isPlanValid()}
                | plan length acceptable: ${explanation.isPlanLengthAcceptable()}
                | operators missing: $operatorsMissing
                | idempotent operators causing errors: ${explanation.areIdempotentOperatorsPresent().entries.map {
                it.value.operator2
            }}
            )
            """.trimIndent()
        }
    }
}
