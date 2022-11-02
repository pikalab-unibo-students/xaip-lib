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
 */
class QuestionPlanSatisfiability(
    override val problem: Problem,
    override val plan: Plan
) : BaseQuestion() {
    override val focus: Operator by lazy { Operator.of(Action.of("", emptyMap(), emptySet(), emptySet())) }
    override val focusOn: Int by lazy { 0 }
    override val newPredicate: Predicate by lazy { Predicate.of("") }
    override val newGroundFluent: Fluent by lazy { Fluent.of(newPredicate) }
    override val newFluent: Fluent by lazy { Fluent.of(newPredicate) }
    override val oldAction: Action by lazy { Action.of("", emptyMap(), emptySet(), emptySet()) }
    override val newAction: Action by lazy { Action.of("", emptyMap(), emptySet(), emptySet()) }
    override val hDomain: Domain by lazy { Domain.of("", emptySet(), emptySet(), emptySet()) }
    override fun buildHypotheticalDomain(): Domain = throw UnsupportedOperationException()

    override fun buildHypotheticalProblem(): Sequence<Problem> = throw UnsupportedOperationException()
}
