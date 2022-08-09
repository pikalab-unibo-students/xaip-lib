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

    /**
     * */
    operator fun String.invoke(vararg targets: String) {
        // TODO("Implement this")
    }

    /**
     * Method that updates the internal list of [goals] adding the last one created.
     */
    operator fun Goal.unaryPlus() {
        goals += (this as FluentBasedGoal)
    }
}
