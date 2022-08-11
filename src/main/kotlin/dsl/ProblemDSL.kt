package dsl

import Domain
import Goal
import ObjectSet
import Problem
import State
import dsl.provider.PredicateProvider

/**
 * Class representing a [Problem] in the DSL.
 */
class ProblemDSL() {
    // TODO io non metterei un DomainDSL qui. quando crei il problem, il domain si suppone che tu ce lo abbia già. metterei un semplice riferimento a un'istanza di domain
    lateinit var domain: Domain
    lateinit var objects: ObjectSet
    lateinit var state: State
    lateinit var goal: Goal

    // TODO non fare campi che possono essere variabili locali. I provide sono oggetti lightweight. Non c'è problema a crearli on the fly quuando servono
    private var predicateProvider = PredicateProvider.of(domain)

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun objects(f: ObjectSetDSL.() -> Unit) {
        val objectsDSL = ObjectSetDSL()
        objectsDSL.f()
        this.objects = objectsDSL.objectSet
    }

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun goals(f: GoalDSL.() -> Unit) {
        val goalDSL = GoalDSL(predicateProvider)
        goalDSL.f()
        this.goal = goalDSL.toGoal()
    }

    /**
     * */
    fun initialState(f: StateDSL.() -> Unit) {
        val stateDSL = StateDSL(predicateProvider)
        stateDSL.f()
        this.state = stateDSL.toState()
    }

    /**
     *  Method responsible that build an instance of [ProblemDSL] and converts it to a [Domain].
     */
    fun buildProblem(): Problem =
        Problem.of(domain, objects, state,goal)

}

/**
 * Entry point for [ProblemDSL] creation.
 */
fun problem(f: ProblemDSL.() -> Unit): Problem {
    return ProblemDSL().also(f).buildProblem()
}
