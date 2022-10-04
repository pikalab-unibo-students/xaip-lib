package explanation.impl

import Domain
import Operator
import Plan
import Problem
import State
import explanation.Question

/**
 * Why operator instead of.
 */
class Question3(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int,
    inState: State? = null,
    val focus2: Operator
) : Question, AbstractQuestion() {
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
    private val newState = newProblem.initialState.apply(focus).first()
    override var hDomain = buildHdomain()

    override fun buildHdomain(): Domain = Domain.of(
        name = newProblem.domain.name,
        predicates = newProblem.domain.predicates,
        actions = newProblem.domain.actions,
        types = newProblem.domain.types
    )

    override fun buildHproblem(): Problem = Problem.of(
        domain = hDomain,
        objects = newProblem.objects,
        initialState = newState,
        goal = newProblem.goal
    )

    override fun isPlanValid(pla: Plan, problem: Problem): Boolean = planValidation(plan, problem)

}
