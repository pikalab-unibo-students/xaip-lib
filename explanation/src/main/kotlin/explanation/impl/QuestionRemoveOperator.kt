package explanation.impl

import core.Domain
import core.Operator
import core.Plan
import core.Problem
import explanation.utils.findAction

/**
 *  Class representing a request from a user not to use an operator in a plan.
 */
class QuestionRemoveOperator(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator
) : BaseQuestion() {
    override val focusOn: Int by lazy { -1 }
    override val newPredicate by lazy { createNewPredicate(focus, "not_done_") }
    override val newFluent by lazy { createNewFluent(focus, newPredicate) }
    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)

    override val oldAction by lazy {
        findAction(focus, problem.domain.actions)
    }

    override val newAction by lazy { createNewAction(oldAction, newFluent, true) }

    override val hDomain by lazy { buildHypotheticalDomain() }

    override fun buildHypotheticalDomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHypotheticalProblem(): Sequence<Problem> =
        sequenceOf(buildHproblem(hDomain, problem, newGroundFluent, null, true))
}
