package explanation.impl

import Plan
import Planner
import explanation.Explainer
import explanation.Explanation
import explanation.Question

class ExplainerImpl(override val planner: Planner) : Explainer {
    override fun explain(question: Question): Explanation = Explanation.of(question, this)
    override fun minimalPlanSelector(f: () -> Plan): Plan = f.invoke()
}
