package dsl

import Domain
import Goal
import ObjectSet
import Problem
import State
import dsl.provider.PredicateProvider
import dsl.provider.TypeProvider

/**
 * Class representing a [Problem] in the DSL.
 */
class ProblemDSL(val domain: Domain) {
    var objects: ObjectSet = ObjectSet.of(emptyMap())
    var state: State = State.of(emptySet())
    var goal: Goal = FluentBasedGoal.of()

    private var predicateProvider = PredicateProvider.of(domain.predicates)
    private var typesProvider = TypeProvider.of(domain.types)
    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun objects(f: ObjectSetDSL.() -> Unit) {
        val objectsDSL = ObjectSetDSL(typesProvider)
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
        Problem.of(domain, objects, state, goal)
}

/**
 * Entry point for [ProblemDSL] creation.
 */
fun problem(domain: Domain, f: ProblemDSL.() -> Unit): Problem {
    return ProblemDSL(domain).also(f).buildProblem()
}
