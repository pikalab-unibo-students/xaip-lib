package dsl

import Fluent
import FluentBasedGoal
import Goal
import dsl.provider.PredicateProvider

/**
 * Class representing a [FluentBasedGoal] in the DSL.
 */

class GoalDSL(
    private val predicateProvider: PredicateProvider
) : AbstractFluentDSL(predicateProvider) {

    var fluents: MutableSet<Fluent> = mutableSetOf()
    operator fun Fluent.unaryPlus() = fluents.add(this)

    fun toGoal(): FluentBasedGoal = FluentBasedGoal.of(fluents)
}
