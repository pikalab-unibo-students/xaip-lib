package explanation

import explanation.impl.ExplanationPresenterImpl

/**
 *
 */
interface ExplanationPresenter {
    val explanation: Explanation

    /**
     *
     */
    fun present(): String

    companion object {
        /**
         * Factory method for an [Explanation] creation.
         */
        fun of(
            explanation: Explanation
        ): ExplanationPresenter = ExplanationPresenterImpl(explanation)
    }
}
