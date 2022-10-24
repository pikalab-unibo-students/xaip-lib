package explanation

import core.Plan
import core.Planner
import explanation.impl.ExplainerImpl

// Problem explainer si aspetta che il planner abbia un metodo plan, ì
// devo recuperare comunque un piano (quindi nel nostro caso mi tocca chiamare la first
// Problema explanation ora come ora non ha metodi se non isValid()
// quindi explain non può restiutire un explanation m(cioé può ma per me non ha molto un  verso,
// quindi  per il momento gli faccio restituire l'expolanation pèerc ome è ora,
// ovvero il toString(generato da explanation).
/**
 *
 */
interface Explainer {

    val planner: Planner
    val question: Question

    /**
     *
     */
    fun explain(): Explanation

    /**
     *
     */
    fun minimalPlanSelector(): Plan

    companion object {
        /**
         *
         */
        fun of(planner: Planner, question: Question): Explainer = ExplainerImpl(planner, question)
    }
}
