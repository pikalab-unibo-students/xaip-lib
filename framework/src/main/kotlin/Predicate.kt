import impl.PredicateImpl

/**
 * A [Predicate] are facts that are interesting (i.e. properties of objects), which can be true or false.
 */
interface Predicate {
    /**
     *
     */
    val name: String

    /**
     *
     */
    val arguments: List<Type>

    companion object {
        /**
         * Factory method for an [Predicate] creation.
         */
        fun of(name: String, arguments: List<Type>): Predicate = PredicateImpl(name, arguments)

        /**
         * Factory method for an [Predicate] creation.
         */
        fun of(name: String, vararg arguments: Type): Predicate = PredicateImpl(name, listOf(*arguments))
    }
}
