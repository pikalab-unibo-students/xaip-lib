package explanation

import Domain
import Operator
import Plan
import Problem

/**
 *
 */
interface Question : GeneralQuestion {
    // val problem: Problem
    // val plan: Plan
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
}

/**
 * */
interface GeneralQuestion {
    val problem: Problem
    val plan: Plan
}
