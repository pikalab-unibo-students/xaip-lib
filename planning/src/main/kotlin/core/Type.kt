package core

import core.impl.TypeImpl

/**
 * Generic entity for the objects' types.
 */
interface Type {
    /**
     * @property name: [Type]'s name.
     */
    val name: String

    /**
     * @property superType: supertype of the current [Type], if it exists.
     */
    val superType: Type?

    companion object {
        /**
         * Factory method for an [Type] creation.
         */
        @JvmOverloads
        fun of(name: String, superType: Type? = null): Type = TypeImpl(name, superType)
    }
}
