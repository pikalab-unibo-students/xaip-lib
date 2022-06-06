package impl

import Type

internal data class TypeImpl(override val name: String, override val superType: Type? = null) : Type {
    override fun toString(): String = name
}