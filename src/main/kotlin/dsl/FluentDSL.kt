package dsl

import Fluent
import Variable
import dsl.provider.PredicateProvider
import dsl.provider.VariableProvider

/**
 * Class representing an [Fluent] in the DSL.
 */
class FluentDSL(
    predicateProvider: PredicateProvider,
    private val variableProvider: VariableProvider
) : AbstractFluentDSL(predicateProvider) {
    val fluents: MutableSet<Fluent> = mutableSetOf()

    /**
     * Method that updates the internal list of [fluents] adding the last one created.
     */
    operator fun Fluent.unaryPlus() {
        fluents += this
    }

    /**
     *
     */
    operator fun String.unaryPlus() {
        fluents += this()
    }
}
