package explanation.impl

import Domain
import Operator
import Plan
import Problem

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
    override var newPredicate = createNewPredicate(focus, "has_done_")
    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)
    override var newFluent = createNewFluent(focus, newPredicate)
    override var oldAction =
        findAction(focus, problem.domain.actions)
    override var newAction = createNewAction(oldAction, newFluent)
    override var hDomain = buildHypotheticalDomain()

    override fun buildHypotheticalDomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHypotheticalProblem(): Problem = buildHproblem(hDomain, problem, newGroundFluent, null)
}
