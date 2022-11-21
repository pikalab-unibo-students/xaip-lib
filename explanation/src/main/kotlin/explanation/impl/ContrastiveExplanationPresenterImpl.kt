package explanation.impl

import explanation.ContrastiveExplanationPresenter
import explanation.Explanation

internal class ContrastiveExplanationPresenterImpl(explanation: Explanation) :
    ContrastiveExplanationPresenter,
    BaseExplanationPresenter(explanation) {

    override fun presentContrastiveExplanation(): String {
        return """Contrastive explanation:
            |  $isProblemSolvable
            |  the original plan was: ${explanation.originalPlan.operators},
            |  the new plan is: ${explanation.novelPlan.operators} is valid: ${explanation.isPlanValid()},
            |  ${ExplanationImpl::addedList.name}=${explanation.addedList},
            |  ${ExplanationImpl::deletedList.name}=${explanation.deletedList},
            |  ${ExplanationImpl::sharedList.name}=${explanation.sharedList}
            |  
        """.trimMargin()
    }
}
