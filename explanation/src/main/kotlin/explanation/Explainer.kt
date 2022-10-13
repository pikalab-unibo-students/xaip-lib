package explanation

import Plan
import Planner

// Problem explainer si aspetta che il planner abbia un metodo plan, devo recuperare comunque un piano (quindi nel nostro caso mi tocca chiamare la first
// Problema explanation ora come ora non ha metodi se non isValid() quindi explain non può restiutire un explanation m(cioé può ma per me non ha molto un  verso,
// quindi  per il momento gli faccio restituire l'expolanation pèerc ome è ora, ovverop il to>String(9 generato da explanation
class Explainer(val planner: Planner) {

    fun explain(question: Question) =
        Explanation.of(question.plan, planner.plan(question.problem).first(), question).toString()

    // fun minimalPlanSelector(f: Planner.() -> Plan) {}
}
