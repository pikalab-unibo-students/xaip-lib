import impl.TypeImpl

/**
 * A type in PDDL language should have a [name] and optionally a supertype if it is a subtype.
 */
interface Type {
    val name: String
    val superType: Type?

    companion object {
        @JvmOverloads
        fun of(name: String, superType: Type? = null): Type = TypeImpl(name, superType)
    }
}