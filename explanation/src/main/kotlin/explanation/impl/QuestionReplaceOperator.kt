package explanation.impl

import Action
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
    override val oldAction: Action by lazy { Action.of("", emptyMap(), emptySet(), emptySet()) }
    override val newAction: Action by lazy { Action.of("", emptyMap(), emptySet(), emptySet()) }

    private val newProblem by lazy {
        if (inState != null) {
            Problem.of(
                domain = problem.domain,
                objects = problem.objects,
                initialState = inState,
                goal = problem.goal
            )
        } else {
            problem
        }
    }

    // A. TODO( estendi a considerare tutti gli stati possibili)
    private val newState = newProblem.initialState.apply(insteadOf)
    override var hDomain = buildHypotheticalDomain()

    override fun buildHypotheticalDomain(): Domain = Domain.of(
        name = newProblem.domain.name,
        predicates = newProblem.domain.predicates,
        actions = newProblem.domain.actions,
        types = newProblem.domain.types
    )

    override fun buildHypotheticalProblem(): Sequence<Problem> = sequence {
        for (state in newState) {
            yield(
                Problem.of(
                    domain = hDomain,
                    objects = newProblem.objects,
                    initialState = state,
                    goal = newProblem.goal
                )
            )
        }
    }
}
