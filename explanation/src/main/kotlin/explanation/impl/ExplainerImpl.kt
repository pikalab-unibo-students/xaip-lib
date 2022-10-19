package explanation.impl

import Plan
import Planner
import explanation.Explainer
import explanation.Explanation
import explanation.Question

class ExplainerImpl(override val planner: Planner, override val question: Question) : Explainer {

    override fun explain(): Explanation = Explanation.of(question, this)
    override fun minimalPlanSelector(): Plan = planner.plan(question.problem).first()
}
