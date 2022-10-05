package explanation.impl

import Domain
import Operator
import Plan
import Problem
import explanation.Question

/**
 * Why operator not used.
 * Add operator to the plan.
 */
class Question1(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : Question, AbstractQuestion() {
    override var newPredicate = createNewPredicate(focus, "has_done_")
    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)
    override var newFluent = createNewFluent(focus, newPredicate)
    override var oldAction =
        findAction(focus, problem.domain.actions)
    override var newAction = createNewAction(oldAction, newFluent)
    override var hDomain = buildHdomain()

    override fun buildHdomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHproblem(): Problem = buildHproblem(hDomain, problem, newGroundFluent, null)
    override fun isPlanValid(plan: Plan, problem: Problem): Boolean = planValidation(plan, problem)

}
