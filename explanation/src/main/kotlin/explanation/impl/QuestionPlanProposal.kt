package explanation.impl

import Domain
import Operator
import Plan
import Predicate
import Problem

/**
 * Entity representing the question: "Why not this plan instead?"; * aka a proposal for a new plan.
 * * @property alternativePlan: [Plan] proposed by the user.
 */
class QuestionPlanProposal(
    override val problem: Problem,
    val alternativePlan: Plan,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : BaseQuestion() {
    override val newPredicate: Predicate by lazy { Predicate.of("") }
    override fun buildHypotheticalDomain(): Domain = throw UnsupportedOperationException()

    override fun buildHypotheticalProblem(): Problem = throw UnsupportedOperationException()
}
