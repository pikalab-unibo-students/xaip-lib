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
    override var newPredicate = createNewPredicate(focus, "not_done_")
    override var newFluent = createNewFluent(focus, newPredicate)
    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)

    override var oldAction =
        findAction(focus, problem.domain.actions)

    override var newAction = createNewAction(oldAction, newFluent, true)

    override var hDomain = buildHypotheticalDomain()

    override fun buildHypotheticalDomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHypotheticalProblem(): Problem = buildHproblem(hDomain, problem, newGroundFluent, null, true)
}
