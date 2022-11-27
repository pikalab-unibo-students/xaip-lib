package explanation.impl

import core.Action
import core.Domain
import core.Fluent
import core.Operator
import core.Plan
import core.Predicate
import core.Problem
import core.State

/**
 * Why operator an instead of b in state C.
 * core.Operator substitution in a state.
 * */
class QuestionReplaceOperator(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int,
    val inState: State? = null
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

    private val newStates = newProblem.initialState.apply(focus)
    override var hDomain = buildHypotheticalDomain()

    override fun buildHypotheticalDomain(): Domain = problem.domain

    override fun buildHypotheticalProblem(): Sequence<Problem> = sequence {
        require(newStates.toSet().isEmpty()) {"Error $focus not applicable!"}
        for (state in newStates) {
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
