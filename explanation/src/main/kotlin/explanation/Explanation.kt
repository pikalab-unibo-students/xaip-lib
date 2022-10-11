package explanation

import Operator
import Plan
import explanation.impl.ExplanationImpl

/**
 *An [Explanation] is an entity that give a contrastive explanation about a plan.
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
     * Method that states if a pla  is a solution for a given problem.
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
