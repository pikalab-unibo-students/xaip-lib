package explanation.impl

import Domain
import Operator
import Plan
import Problem
import explanation.Question

/**
 * Why is plan not good (solvable).
 */
class Question5(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    val alternativePlan: Plan
) :
    Question {
    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}
