package dsl

import Domain
import FluentBasedGoal
import Object
import Problem
import State

/**
 * Class representing a [Problem] in the DSL.
 */
class ProblemDSL {
    var domain: Domain = TODO("recupera un'istanza di domain creata")
    var objects: List<Object> = emptyList() // Object o ObjectSet???
    var state: State = TODO() // recupera lo stato iniziale
    var goals: List<FluentBasedGoal> = emptyList()

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
        val goalsDSL = GoalDSL()

        goalsDSL.f()
        this.goals = goalsDSL.goals
    }

    fun initialState(f: StateDSL.() -> Unit) {
        TODO()
    }

    /**
     *  Method responsible that build an instance of [PRoblemDSL] and converts it to a [Domain].
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

