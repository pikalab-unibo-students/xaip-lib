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
    val alternativePlan: Plan,
    override val focusOn: Int
) :
    Question {
    override fun buildHdomain(): Domain = throw UnsupportedOperationException()

    override fun buildHproblem(): Problem = throw UnsupportedOperationException()

    override fun isPlanValid(): Boolean {
        TODO("Not yet implemented")
    }
}
