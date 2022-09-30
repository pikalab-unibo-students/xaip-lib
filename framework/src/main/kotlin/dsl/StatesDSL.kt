package dsl

import Fluent
import State
import dsl.provider.PredicateProvider

/**
 * Class representing a [State] in the DSL.
 */
class StatesDSL(
    predicateProvider: PredicateProvider
) : AbstractFluentDSL(predicateProvider) {

    var fluents: MutableSet<Fluent> = mutableSetOf()

    /**
     * Method that updates the internal list of [fluents] adding the last one created.
     */
    operator fun Fluent.unaryPlus() = fluents.add(this)

    /**
     * Method that create a [Fluent] from a [String] without arguments.
     */
    operator fun String.unaryPlus() {
        fluents.add(this())
    }

    /**
     * Method responsible for the creation of the [State].
     */
    fun toState(): State = State.of(fluents)
}
