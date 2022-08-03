package dsl

import Type

/**
 * Class representing a [Type] in the DSL.
 */
class TypeDSL {
    val types: MutableSet<Type> = mutableSetOf()

    /**
     * Method that allow to treat a [String] as it was a [Type].
     */
    operator fun String.invoke(superType: String): Type {
        return Type.of(this, types.find { it.name == superType } ?: error("Missing type: $superType"))
    }

    /**
     * Method that updates the internal list of [types] adding the last one created.
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
