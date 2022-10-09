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
 * @property focus2: operator to replace in the plan.
 * */
class Question3(
    override val problem: Problem,
    override val plan: Plan,
    override val focus: Operator,
    override val focusOn: Int,
    inState: State? = null,
    val focus2: Operator
) : Question, AbstractQuestion() {
    // se mi passano uno stato in cui vorrebbero che rimuovessi l'azione bella,
    // se no assumo come stato di default quello iniziale.
    // non so quanto wquesta assunzione sia safe;
    // l'ho tenuta più per compatibilità con il paper che altro.
    // NB Il paper lo stato non se lo faceva dare e usava quello iniziale
    // (se non erro).
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
