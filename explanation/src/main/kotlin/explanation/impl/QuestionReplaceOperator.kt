package explanation.impl

import Domain
import Operator
import Plan
import Problem
import State
import explanation.Question

/**
 * Why operator a instead of b in state C.
 * Operator substitution in a state.
 * @property focus2: [Operator] that must replace [focus] in the [plan].
 * */
class QuestionReplaceOperator(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int,
    inState: State? = null,
    val focus2: Operator
) : Question, BaseQuestion() {
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
    private val newState = newProblem.initialState.apply(focus2).first()
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
