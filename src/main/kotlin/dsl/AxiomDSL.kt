package dsl

import Axiom
import Expression
import Type
import Variable
import dsl.provider.VariableProvider

/**
 * Class representing an [Axiom] in the DSL.
 */
class AxiomDSL {
    var parameters: Map<Variable, Type> = emptyMap()
    val context: MutableSet<Expression> = mutableSetOf()
    val implies: MutableSet<Expression> = mutableSetOf()
    private val variableProvider: VariableProvider = VariableProvider.of(this)

    /**
     * Method responsible for the axiom of the action's parameters.
     */
    fun parameters(f: ParametersDSL.() -> Unit) {
        parameters = ParametersDSL(variableProvider).also(f).parameters
    }
}
