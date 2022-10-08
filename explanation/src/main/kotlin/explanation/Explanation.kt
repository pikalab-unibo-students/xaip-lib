package explanation

import Operator
import Plan
import explanation.impl.ExplanationImpl

/**
 *
 */
interface Explanation {
    /**
     *
     */
    val originalPlan: Plan

    /**
     *
     */
    val novelPlan: Plan

    /**
     *
     */
    val question: Question

    /**
     *
     */
    val addList: List<Operator>

    /**
     *
     */
    val deleteList: List<Operator>

    /**
     *
     */
    val existingList: List<Operator>

    /**
     *
     */
    fun isPlanValid(): Boolean

    companion object {
        /**
         * Factory method for an [Explanation] creation.
         */
        fun of(
            originalPlan: Plan,
            novelPlan: Plan,
            question: Question
        ): Explanation = ExplanationImpl(originalPlan, novelPlan, question)
    }
}
