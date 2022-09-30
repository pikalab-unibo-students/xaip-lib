package explanation

import Domain
import Operator
import Plan
import Problem

interface Question {
    val problem: Problem
    val plan: Plan
    val focus: Operator

    fun buildHdomain(): Domain
    fun buildHproblem(): Problem
}









