package explanation.impl

import Domain
import Operator
import Plan
import Problem
import explanation.Question

/**
 * Why is plan not good (solvable).
 * Checking for a plan satisfiability.
 */
class Question5(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    private val alternativePlan: Plan,
    override val focusOn: Int
) :
    Question, AbstractQuestion() {
    override fun buildHdomain(): Domain = throw UnsupportedOperationException()

    override fun buildHproblem(): Problem = throw UnsupportedOperationException()
}
