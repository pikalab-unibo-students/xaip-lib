package explanation.impl

import core.* // ktlint-disable no-wildcard-imports
import explanation.utils.findAction

/**
 * Class representing a request from a user to add an operator to the plan.
 *
 * @property focus: operator to add to the plan
 * @property focusOn: index of operator
 */
class QuestionAddOperator(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int
) : BaseQuestion() {
    private val emptyAction: Action by lazy { Action.of("", emptyMap(), emptySet(), emptySet()) }
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
