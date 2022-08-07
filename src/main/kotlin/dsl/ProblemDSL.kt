package dsl

import Domain
import FluentBasedGoal
import Object
import Problem
import State
import dsl.provider.PredicateProvider
import dsl.provider.TypesProvider

/**
 * Class representing a [Problem] in the DSL.
 */
class ProblemDSL {
    var domain: DomainDSL = TODO("recupera un'istanza di domain creata")
    var objects: MutableSet<Object> = mutableSetOf() // TODO("Object o ObjectSet???")
    var state: MutableSet<State> = mutableSetOf()
    var goals: MutableSet<FluentBasedGoal> = mutableSetOf()
    private var predicateProvider = PredicateProvider.of(domain)
    private var typesProvider = TypesProvider.of(domain)

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
        val goalsDSL = GoalDSL(predicateProvider)
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
