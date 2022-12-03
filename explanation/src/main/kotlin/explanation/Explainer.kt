package explanation

import core.Plan
import core.Planner
import core.Problem
import explanation.impl.ExplainerImpl

/**
 * Explainer for all types of explanations.
 */
interface Explainer {

    val planner: Planner

    /**
     * returns an [Explanation] for a the [question].
     */
    fun explain(question: Question): Explanation

    /**
     * returns the minimal solution for the given [problem].
     */
    fun minimalPlanSelector(problem: Problem): Plan

    companion object {
        /**
         * Factory method for an [Explainer] creation.
         */
        fun of(planner: Planner): Explainer = ExplainerImpl(planner)
    }
}
