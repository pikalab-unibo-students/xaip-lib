package explanation.impl

import Domain
import Operator
import Plan
import Problem
import explanation.Question

/**
 * Why operator used.
 */
class Question2(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : Question, AbstractQuestion() {
    override var newPredicate = createNewPredicate(focus, "not_done_", true)
    override var newFluent = createNewFluent(focus, newPredicate)
    override var newGroundFluent = createNewGroundFluent(focus, newPredicate)

    override var oldAction =
        findAction(focus, problem.domain.actions)

    override var newAction = createNewAction(oldAction, newFluent, true)

    override var hDomain = buildHdomain()

    override fun buildHdomain(): Domain = buildHdomain(problem.domain, newPredicate, newAction)

    override fun buildHproblem(): Problem = buildHproblem(hDomain, problem, newGroundFluent, null, true)
    override fun isPlanValid(): Boolean {
        TODO("Not yet implemented")
    }
}
