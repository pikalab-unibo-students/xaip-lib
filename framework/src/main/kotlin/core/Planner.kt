package core

import core.impl.StripsPlanner
/**
 * Interface that represents the algorithm to be used to solve the [Problem].
 */
interface Planner {
    /**
     * Method used to compute the plan to reach the [Goal].
     */
    fun plan(problem: Problem): Sequence<Plan>

    companion object {
        /**
         * Factory method for an [StripsPlanner] creation.
         */
        fun strips(): Planner = StripsPlanner()
    }
}
