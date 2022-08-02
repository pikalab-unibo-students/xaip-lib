package impl.dsl

import Domain
import FluentBasedGoal
import Object
import Problem
import State

/**
 * Class representing a [Problem] in the DSL.
 */
class ProblemDSL {
    var domain: Domain = TODO()
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

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun buildProblem(): Problem {
        TODO()
    }
}
