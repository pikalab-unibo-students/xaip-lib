package dsl

import Axiom
import Fluent
import Type
import Variable

/**
 * Class representing an [Axiom] in the DSL.
 */
class AxiomDSL {
    var parameters: Map<Variable, Type> = emptyMap()
    val context: MutableSet<Fluent> = mutableSetOf()
    val implies: MutableSet<Fluent> = mutableSetOf()
    /**
     * Method responsible for the axiom of the action's parameters.
     */
    fun parameters(f: ParametersDSL.() -> Unit) {
        parameters = ParametersDSL().also(f).parameters
    }


    /**
     * Method responsible for the creation of the action's preconditions.
     */
    fun preconditions(f: FluentDSL.() -> Unit) {
        preconditions += FluentDSL(predicateProvider).also(f).fluents
    }
}
