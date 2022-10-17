package explanation.impl

import Plan
import Planner
import explanation.Explainer
import explanation.Explanation
import explanation.Question

class ExplainerImpl(override val planner: Planner) : Explainer {
    override fun explain(question: Question): Explanation {
        TODO("Not yet implemented")
    }

    override fun minimalPlanSelector(f: Planner.() -> Plan): Plan {
        TODO("Not yet implemented")
    }
}