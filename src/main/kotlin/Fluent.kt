import impl.FluentImpl

/**
 * A fluent represents a predicate ([instanceOf]) at runtime.
 * It states its truthiness/falseness.
 */
interface Fluent : Applicable<Fluent> {
    val name: String
    val args: List<Value>
    val instanceOf: Predicate
    val isNegated: Boolean
    val isGround: Boolean

    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    operator fun not(): Fluent

    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun match(other: Fluent): Boolean

    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun mostGeneralUnifier(other: Fluent): VariableAssignment

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(instanceOf: Predicate, isNegated: Boolean = false, args: List<Value>): Fluent =
            FluentImpl(instanceOf, isNegated, args)

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(instanceOf: Predicate, isNegated: Boolean = false, vararg args: Value): Fluent =
            FluentImpl(instanceOf, isNegated, listOf(*args))

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun positive(instanceOf: Predicate, vararg args: Value): Fluent =
            FluentImpl(instanceOf, false, listOf(*args))

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun negative(instanceOf: Predicate, vararg args: Value): Fluent =
            FluentImpl(instanceOf, true, listOf(*args))
    }
}
