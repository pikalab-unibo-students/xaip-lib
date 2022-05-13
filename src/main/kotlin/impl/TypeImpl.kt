package impl

import Type

data class TypeImpl(override val name: String, override val superType: Type? = null): Type