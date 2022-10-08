package explanation.impl

import Domain
import Operator
import Plan
import Problem
import explanation.Question

/**
 * Why not this plan instead.
 * Proposal for a new plan.
 * @property alternativePlan: [Plan] proposed by the user.
 */
class Question4(
    override val problem: Problem,
    val alternativePlan: Plan,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : Question, AbstractQuestion() {
    override fun buildHdomain(): Domain = throw UnsupportedOperationException()

    override fun buildHproblem(): Problem = throw UnsupportedOperationException()
}
