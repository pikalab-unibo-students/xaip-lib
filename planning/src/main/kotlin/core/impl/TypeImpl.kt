package core.impl

import core.Type

internal data class TypeImpl(override val name: String, override val superType: Type? = null) : Type {
    override fun toString(): String = name
}
