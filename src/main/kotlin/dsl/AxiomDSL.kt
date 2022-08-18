package dsl

import Axiom
import Expression
import Type
import Variable
import dsl.provider.PredicateProvider
import dsl.provider.TypeProvider
import dsl.provider.VariableProvider

/**
 * Class representing an [Axiom] in the DSL.
 */
class AxiomDSL(
    predicateProvider: PredicateProvider,
    val typeProvider: TypeProvider
) : AbstractFluentDSL(predicateProvider) {
    var parameters: Map<Variable, Type> = mutableMapOf()
    lateinit var context: Expression
    lateinit var implies: Expression
    private val variableProvider: VariableProvider = VariableProvider.of()

    /**
     * Method responsible for the axiom of the action's parameters.
     */
    fun parameters(f: ParametersDSL.() -> Unit) {
        parameters = ParametersDSL(variableProvider, typeProvider).also(f).parameters
    }

    /**
     * Method used to build the [Axiom].
     * */
    fun toAxiom() = Axiom.of(parameters, context, implies)
}
