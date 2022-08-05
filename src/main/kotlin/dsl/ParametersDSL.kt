package dsl

import Type
import Variable

/**
 * Class representing the [parameters] of an [ActionDSL].
 */
class ParametersDSL {

    val parameters: MutableMap<Variable, Type> = mutableMapOf()

    /**
     * Method responsible for the creation of the [parameters]' map.
     */
    infix fun String.ofType(type: String) {
        val variable = Variable.of(this)
        val t = Type.of(type)
        parameters[variable] = t
    }
}
