package explanation.impl

import core.Plan
import core.Planner
import core.Problem
import explanation.Explainer
import explanation.Explanation
import explanation.Question

/**
 * Reification of the [Explainer] interface.
 */
internal data class ExplainerImpl(override val planner: Planner) : Explainer {

    override fun explain(question: Question): Explanation = Explanation.of(question, this)
    override fun minimalPlanSelector(problem: Problem): Plan = planner.plan(problem).first()
}
