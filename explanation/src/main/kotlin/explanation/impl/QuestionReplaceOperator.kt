package explanation.impl

import Domain
import Fluent
import Operator
import Plan
import Predicate
import Problem
import State

/**
 * Why operator a instead of b in state C.
 * Operator substitution in a state.
 * @property insteadOf: [Operator] that must replace [focus] in the [plan].
 * */
class QuestionReplaceOperator(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int,
    inState: State? = null,
    val insteadOf: Operator
) : BaseQuestion() {
    override val newPredicate: Predicate by lazy { Predicate.of("") }
    override val newGroundFluent: Fluent by lazy { Fluent.of(newPredicate) }
    override val newFluent: Fluent by lazy { Fluent.of(newPredicate) }

    private val newProblem = if (inState != null) {
        Problem.of(
            domain = problem.domain,
            objects = problem.objects,
            initialState = inState,
            goal = problem.goal
        )
    } else {
        problem
    }

    // A. TODO( estendi a considerare tutti gli stati possibili)
    private val newState = newProblem.initialState.apply(insteadOf).first()
    override var hDomain = buildHypotheticalDomain()

    override fun buildHypotheticalDomain(): Domain = Domain.of(
        name = newProblem.domain.name,
        predicates = newProblem.domain.predicates,
        actions = newProblem.domain.actions,
        types = newProblem.domain.types
    )

    override fun buildHypotheticalProblem(): Problem = Problem.of(
        domain = hDomain,
        objects = newProblem.objects,
        initialState = newState,
        goal = newProblem.goal
    )
}
