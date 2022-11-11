package explanation.impl

import core.Action
import core.Domain
import core.Fluent
import core.Operator
import core.Plan
import core.Predicate
import core.Problem

/**
 * Entity representing the question: "Why not this plan instead?"; * aka a proposal for a new plan.
 * * @property alternativePlan: [Plan] proposed by the user.
 */
class QuestionPlanProposal(
    override val problem: Problem,
    override val plan: Plan,
    val alternativePlan: Plan
) : BaseQuestion() {
    private val emptyAction: Action by lazy { Action.of("", emptyMap(), emptySet(), emptySet()) }
    override val focus: Operator by lazy { Operator.of(emptyAction) }
    override val focusOn: Int by lazy { 0 }
    override val newPredicate: Predicate by lazy { Predicate.of("") }
    override val newGroundFluent: Fluent by lazy { Fluent.of(newPredicate) }
    override val newFluent: Fluent by lazy { Fluent.of(newPredicate) }
    override val oldAction: Action by lazy { emptyAction }
    override val newAction: Action by lazy { emptyAction }
    override val hDomain: Domain by lazy { Domain.of("", emptySet(), emptySet(), emptySet()) }
    override fun buildHypotheticalDomain(): Domain =
        throw UnsupportedOperationException("Reconciliation process not required")

    override fun buildHypotheticalProblem(): Sequence<Problem> =
        throw UnsupportedOperationException("Reconciliation process not required")
}
