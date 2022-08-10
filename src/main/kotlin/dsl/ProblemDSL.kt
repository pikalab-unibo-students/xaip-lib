package dsl

import Domain
import FluentBasedGoal
import Object
import Problem
import State
import dsl.provider.PredicateProvider

/**
 * Class representing a [Problem] in the DSL.
 */
class ProblemDSL {
    // TODO io non metterei un DomainDSL qui. quando crei il problem, il domain si suppone che tu ce lo abbia già. metterei un semplice riferimento a un'istanza di domain
    var domain: DomainDSL = TODO("recupera un'istanza di domain creata")
    // TODO userei objectSet, perchè ti serve quello per costruire un problema
    var objects: MutableSet<Object> = mutableSetOf() // TODO("Object o ObjectSet???")
    var state: MutableSet<State> = mutableSetOf()
    var goals: MutableSet<FluentBasedGoal> = mutableSetOf()

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
    fun goals(f: GoalsDSL.() -> Unit) {
        val goalsDSL = GoalsDSL(predicateProvider)
        goalsDSL.f()
        this.goals = goalsDSL.goals
    }

    /**
     * */
    fun initialState(f: StateDSL.() -> Unit) {
        val statesDSL = StateDSL()
        statesDSL.f()
        this.state = statesDSL.states
    }

    /**
     *  Method responsible that build an instance of [ProblemDSL] and converts it to a [Domain].
     */
    fun buildProblem(): Problem {
        TODO()
    }
}

/**
 * Entry point for [ProblemDSL] creation.
 */
fun problem(f: ProblemDSL.() -> Unit): Problem {
    return ProblemDSL().also(f).buildProblem()
}
