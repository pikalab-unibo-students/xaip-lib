import impl.PredicateImpl

/**
 * A predicate should be as the fluents in strips.
 */
interface Predicate {
    val name: String
    val arguments: List<Type>

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(name: String, arguments: List<Type>): Predicate = PredicateImpl(name, arguments)

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(name: String, vararg arguments: Type): Predicate = PredicateImpl(name, listOf(*arguments))
    }
}
