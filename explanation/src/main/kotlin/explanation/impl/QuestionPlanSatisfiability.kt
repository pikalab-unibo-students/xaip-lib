package explanation.impl

import Domain
import Operator
import Plan
import Predicate
import Problem

/**
 * Why is plan not good (solvable).
 * Checking for a plan satisfiability.
 *
 * @property alternativePlan: [Plan] proposed by the user.
 */
class QuestionPlanSatisfiability(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    val alternativePlan: Plan,
    override val focusOn: Int
) : BaseQuestion() {
    override val newPredicate: Predicate by lazy { Predicate.of("") }
    override fun buildHypotheticalDomain(): Domain = throw UnsupportedOperationException()

    override fun buildHypotheticalProblem(): Problem = throw UnsupportedOperationException()
}
