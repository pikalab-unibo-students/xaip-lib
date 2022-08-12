package dsl

import Fluent
import dsl.provider.PredicateProvider
import dsl.provider.VariableProvider

/**
 * Class representing an [Fluent] in the DSL.
 */
class FluentDSL(
    predicateProvider: PredicateProvider,
    variableProvider: VariableProvider
) : AbstractFluentDSL(predicateProvider) {
    val fluents: MutableSet<Fluent> = mutableSetOf()

    /**
     * Method that updates the internal list of [fluents] adding the last one created.
     */
    operator fun Fluent.unaryPlus() {
        fluents += this
    }
}
