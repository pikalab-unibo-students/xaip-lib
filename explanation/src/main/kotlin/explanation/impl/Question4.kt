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
    val alternativePlan: Plan,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : Question, AbstractQuestion() {
    override fun buildHdomain(): Domain = throw UnsupportedOperationException()

    override fun buildHproblem(): Problem = throw UnsupportedOperationException()
    override fun isPlanValid(pla: Plan, problem: Problem): Boolean = planValidation(plan, problem)

}
