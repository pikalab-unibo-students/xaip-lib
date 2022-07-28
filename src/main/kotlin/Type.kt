import impl.TypeImpl

/**
 * Generic entity for the objects' types.
 * @property name: [Type]'s name.
 * @property superType: supertype of the current [Type], if it exists.
 */
interface Type {
    val name: String
    val superType: Type?

    companion object {
        /***
         * Factory method for an [Type] creation.
         */
        @JvmOverloads
        fun of(name: String, superType: Type? = null): Type = TypeImpl(name, superType)
    }
}
