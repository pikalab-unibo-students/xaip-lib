package explanation.impl

import Domain
import Operator
import Plan
import Problem

/**
 * Why operator used rather not being used.
 * Forbid operator's usage in the plan
 */
class QuestionRemoveOperator(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : BaseQuestion() {
    override val newPredicate by lazy { createNewPredicate(focus, "not_done_") }
    override val newFluent by lazy { createNewFluent(focus, newPredicate) }
    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)

    override val oldAction by lazy {
        findAction(focus, problem.domain.actions)
    }

    override val newAction by lazy { createNewAction(oldAction, newFluent, true) }

    override val hDomain by lazy { buildHypotheticalDomain() }

    override fun buildHypotheticalDomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHypotheticalProblem(): Problem = buildHproblem(hDomain, problem, newGroundFluent, null, true)
}
