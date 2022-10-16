package explanation.impl

import core.Action
import core.Domain
import core.Fluent
import core.Operator
import core.Plan
import core.Predicate
import core.Problem

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
    override val newGroundFluent: Fluent by lazy { Fluent.of(newPredicate) }
    override val newFluent: Fluent by lazy { Fluent.of(newPredicate) }
    override val oldAction: Action by lazy { Action.of("", emptyMap(), emptySet(), emptySet()) }
    override val newAction: Action by lazy { Action.of("", emptyMap(), emptySet(), emptySet()) }
    override val hDomain: Domain by lazy { Domain.of("", emptySet(), emptySet(), emptySet()) }
    override fun buildHypotheticalDomain(): Domain = throw UnsupportedOperationException()

    override fun buildHypotheticalProblem(): Sequence<Problem> = throw UnsupportedOperationException()
}
