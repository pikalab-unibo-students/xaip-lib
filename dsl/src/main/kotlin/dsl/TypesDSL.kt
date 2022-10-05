package dsl

import Type

/**
 * Class representing a [Type] in the DSL.
 */
class TypesDSL {
    val types: MutableSet<Type> = mutableSetOf()

    /**
     * Method that invoked on a [String] creates a [Type] from it and its argument.
     */
    operator fun String.invoke(superType: String): Type {
        return Type.of(this, types.find { it.name == superType } ?: error("Missing type: $superType"))
    }

    /**
     * Method that create a [Type] from a [String] without arguments.
     */
    operator fun String.unaryPlus() {
        types += Type.of(this)
    }

    /**
     * Method that updates the internal list of [types] adding the last one created.
     */
    operator fun Type.unaryPlus() {
        types += this
    }
}
