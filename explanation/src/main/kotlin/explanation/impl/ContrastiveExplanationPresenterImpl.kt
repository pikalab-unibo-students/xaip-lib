package explanation.impl

import explanation.ContrastiveExplanationPresenter
import explanation.Explanation

internal class ContrastiveExplanationPresenterImpl(explanation: Explanation) :
    ContrastiveExplanationPresenter,
    BaseExplanationPresenter(explanation) {

    override fun presentContrastiveExplanation(): String {
        return """Contrastive explanation:
            |  problem: ${explanation.question.problem.goal}
            |  originalPlan: ${explanation.originalPlan.operators},
            |  novelPlan: ${explanation.novelPlan.operators} is valid: ${explanation.isPlanValid()},
            |  ${ExplanationImpl::addedList.name}=${explanation.addedList},
            |  ${ExplanationImpl::deletedList.name}=${explanation.deletedList},
            |  ${ExplanationImpl::sharedList.name}=${explanation.sharedList}
            |  
        """.trimMargin()
    }
}
