package explanation

import explanation.impl.BaseExplanationPresenter

/**
 *
 */
interface ExplanationPresenter {
    val explanation: Explanation

    /**
     *
     */
    fun present(): String

    /**
     *
     */
    fun presentMinimalExplanation(): String
    companion object {
        /**
         * Factory method for an [Explanation] creation.
         */
        fun of(
            explanation: Explanation
        ): ExplanationPresenter = BaseExplanationPresenter(explanation)
    }
}
