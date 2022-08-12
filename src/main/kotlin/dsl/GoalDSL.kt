package dsl

import Fluent
import FluentBasedGoal
import dsl.provider.PredicateProvider

/**
 * Class representing a [FluentBasedGoal] in the DSL.
 */

class GoalDSL(
    predicateProvider: PredicateProvider
) : AbstractFluentDSL(predicateProvider) {

    var fluents: MutableSet<Fluent> = mutableSetOf()

    /**
     * */
    operator fun Fluent.unaryPlus() = fluents.add(this)

    /**
     * */
    fun toGoal(): FluentBasedGoal = FluentBasedGoal.of(fluents)
}
