package dsl

import Domain
import Goal
import Object
import ObjectSet
import Problem
import State
import dsl.provider.PredicateProvider

/**
 * Class representing a [Problem] in the DSL.
 */
class ProblemDSL(val domain: Domain) {
    // TODO io non metterei un DomainDSL qui. quando crei il problem, il domain si suppone che tu ce lo abbia già. metterei un semplice riferimento a un'istanza di domain
    // TODO userei objectSet, perchè ti serve quello per costruire un problema
    var objects: ObjectSet
    lateinit var state: State
    lateinit var goal: Goal

    // TODO non fare campi che possono essere variabili locali. I provide sono oggetti lightweight. Non c'è problema a crearli on the fly quuando servono
    private var predicateProvider = PredicateProvider.of(domain)

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun objects(f: ObjectDSL.() -> Unit) {
        val objectsDSL = ObjectDSL()
        objectsDSL.f()
        this.objects = objectsDSL.objects
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
    fun buildProblem(): Problem {
        Problem.of(domain, objects.toMutableSet(), state,goal)
    }
}

/**
 * Entry point for [ProblemDSL] creation.
 */
fun problem(f: ProblemDSL.() -> Unit): Problem {
    //
}
