package explanation

import explanation.impl.ContrastiveExplanationPresenterImpl

interface ContrastiveExplanationPresenter : ExplanationPresenter {
    /**
     * */
    fun presentContrastiveExplanation(): String

    companion object {
        /**
         * Factory method for an [Explanation] creation.
         */
        fun of(
            explanation: Explanation
        ): ContrastiveExplanationPresenter = ContrastiveExplanationPresenterImpl(explanation)
    }
}
