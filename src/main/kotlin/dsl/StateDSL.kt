package dsl

import Fluent
import State
import dsl.provider.PredicateProvider

/**
 * Class representing a [State] in the DSL.
 */
class StateDSL(
    predicateProvider: PredicateProvider
) : AbstractFluentDSL(predicateProvider) {

    var fluents: MutableSet<Fluent> = mutableSetOf()
    operator fun Fluent.unaryPlus() = fluents.add(this)

    fun toState(): State = State.of(fluents)
}
