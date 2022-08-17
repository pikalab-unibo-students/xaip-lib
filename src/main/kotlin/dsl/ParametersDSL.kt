package dsl

import Type
import Variable
import dsl.provider.TypeProvider
import dsl.provider.VariableProvider

/**
 * Class representing the [parameters] of an [ActionDSL].
 */
class ParametersDSL(var variableProvider: VariableProvider,
                    val typeProvider: TypeProvider) {

    val parameters: MutableMap<Variable, Type> = mutableMapOf()

    /**
     * Method responsible for the creation of the [parameters]' map.
     */
    infix fun String.ofType(type: String) {
        val variable = Variable.of(this)
        variableProvider.addVariable(variable)
        val t = typeProvider.findType(type) ?: error("message")
        parameters[variable] = t
    }
}
