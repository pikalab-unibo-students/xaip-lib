package explanation

import core.Operator
import core.Plan
import explanation.impl.ExplanationImpl

/**
 *An [Explanation] is an entity that give a contrastive explanation about a [Plan].
 */
interface Explanation {
    /**
     * @property originalPlan: represents the original [Plan] the inquirer wants to challenge.
     */
    val originalPlan: Plan

    /**
     * @property novelPlan: represents the new [Plan] containing the inquirer's suggestions.
     */
    val novelPlan: Plan

    /**
     * @property question: represents the question asked by the inquirer.
     */
    val question: Question

    /**
     * @property addList: represents the list of the actions that where not present in the [originalPlan]
     * but are in the [novelPlan].
     */
    val addList: List<Operator>

    /**
     * @property deleteList: represents the list of the actions that where present in the [originalPlan]
     * but are not in the [novelPlan]
     */
    val deleteList: List<Operator>

    /**
     * @property existingList: represents the list of the actions that are present both in the [novelPlan]
     * and in the [originalPlan].
     */
    val existingList: List<Operator>

    /**
     * Method that states if a [Plan] is a solution for a given [Problem].
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
