package explanation.impl

import Domain
import Operator
import Plan
import Problem
import explanation.Question

/**
 * Why not this plan instead.
 */
class Question4(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    val alternativePlan: Plan
) : Question, AbstractQuestion() {

    override fun buildHdomain(): Domain {
        TODO("Not yet implemented")
    }

    override fun buildHproblem(): Problem {
        TODO("Not yet implemented")
    }
}
