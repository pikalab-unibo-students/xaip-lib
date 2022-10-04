package explanation

import Domain
import Operator
import Plan
import Problem
import explanation.impl.Answer

/**
 *
 */
interface Question : GeneralQuestion {
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

    fun isValid(): Boolean
    fun `Where is the problem`(): Answer
}
