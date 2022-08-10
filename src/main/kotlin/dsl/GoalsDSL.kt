package dsl

import FluentBasedGoal
import dsl.provider.PredicateProvider

class GoalsDSL(
    private val predicateProvider: PredicateProvider
) {
    val goals: MutableSet<FluentBasedGoal> = mutableSetOf()

    /**
     * Method that allow to treat a [String] as it was a [Action].
     */
    operator fun String.invoke(f: GoalDSL.() -> Unit) {
        goals.add(GoalDSL(predicateProvider).also(f).toGoal())
    }
}