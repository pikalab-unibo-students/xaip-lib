package impl.dsl

import Type

/**
 * Class representing a [Type] in the DSL.
 */
class TypeDSL {
    val types: MutableList<Type> = mutableListOf()

    operator fun String.invoke(superType: String): Type {
        return Type.of(this, types.find { it.name == superType } ?: error("Missing type: $superType"))
    }

    operator fun String.unaryPlus() {
        types += Type.of(this)
    }

    operator fun Type.unaryPlus() {
        types += this
    }
}
