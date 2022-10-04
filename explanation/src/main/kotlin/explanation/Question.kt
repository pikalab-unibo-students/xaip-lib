package explanation

import Domain
import Operator
import Plan
import Problem

/**
 *
 */
interface Question {
    val problem: Problem
    val plan: Plan
    val focus: Operator
    val focusOn: Int

    /**
     *
     */
    fun buildHdomain(): Domain

    /**
     *
     */
    fun buildHproblem(): Problem

    /**
     * */
    fun isPlanValid(pla: Plan, problem: Problem): Boolean
}
