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
 *
 * @property typeProvider: instance of [TypeProvider].
 */
class AxiomDSL(
    predicateProvider: PredicateProvider,
    val typeProvider: TypeProvider
) : AbstractFluentDSL(predicateProvider) {
    /**
     * @property parameters: map that associates each [Variable] to the corresponding [Type].
     */
    var parameters: Map<Variable, Type> = mutableMapOf()

    /**
     * @property context: set of [Expression] that express what the [Axiom] states.
     */
    lateinit var context: Expression

    /**
     * @property implies: set of [Expression] that express the consequences of the [Axiom].
     */
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
