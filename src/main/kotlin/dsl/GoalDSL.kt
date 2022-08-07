package dsl

import FluentBasedGoal
import Goal
import dsl.provider.PredicateProvider

/**
 * Class representing a [FluentBasedGoal] in the DSL.
 */
class GoalDSL(
    private val predicateProvider: PredicateProvider
) {
    var goals: MutableSet<FluentBasedGoal> = mutableSetOf()

/*
/**
     * Method responsible for the creation of the action's preconditions.
     */
    fun goals(f: FluentDSL.() -> Unit) {
        goals += FluentDSL(predicateProvider).also(f).fluents
    }

 */
    /**
     * */
    operator fun String.invoke(vararg targets: String):Goal {
        TODO("implement this")
    }

    /**
     * Method that updates the internal list of [goals] adding the last one created.
     */
    operator fun Goal.unaryPlus() {
        TODO("implement this")
    }
}
