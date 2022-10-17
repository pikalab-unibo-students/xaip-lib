package explanation.impl

import Domain
import Operator
import Plan
import Problem
import explanation.utils.findAction

/**
 * Why operator not used.
 * Add operator to the plan.
 */
class QuestionAddOperator(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : BaseQuestion() {
    override val newPredicate by lazy { createNewPredicate(focus, "has_done_") }
    override val newGroundFluent by lazy { createNewGroundFluent(focus, newPredicate) }
    override val newFluent by lazy { createNewFluent(focus, newPredicate) }
    override val oldAction by lazy {
        findAction(focus, problem.domain.actions)
    }
    override val newAction by lazy { createNewAction(oldAction, newFluent) }
    override val hDomain by lazy { buildHypotheticalDomain() }

    override fun buildHypotheticalDomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHypotheticalProblem(): Sequence<Problem> =
        sequenceOf(buildHproblem(hDomain, problem, newGroundFluent, null))
}