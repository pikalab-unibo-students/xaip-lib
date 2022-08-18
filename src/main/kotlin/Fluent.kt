import impl.FluentImpl

/**
 * A [Fluent] represents a predicate ([instanceOf]) at runtime.
 * It states its truthiness/falseness.
 */
interface Fluent : Applicable<Fluent>, Expression {
    val name: String
    val args: List<Value>
    val instanceOf: Predicate
    val isNegated: Boolean
    val isGround: Boolean

    /**
     * Method responsible for the negation of the [isNegated] field.
     */
    fun negate(): Fluent

    /**
     * Method that checks if two fluents unify.
     * @return true if they do or, false otherwise.
     */
    fun match(other: Fluent): Boolean

    /**
     * Method that look for the most general unifier among two fluents.
     * @return the mgu, if it finds one, or an exception if no mgu exists.
     */
    fun mostGeneralUnifier(other: Fluent): VariableAssignment

    companion object {
        /**
         * Factory method for an [Fluent] creation.
         */
        fun of(instanceOf: Predicate, isNegated: Boolean = false, args: List<Value>): Fluent =
            FluentImpl(instanceOf, isNegated, args)

        /**
         * Factory method for an [Fluent] creation.
         */
        fun of(instanceOf: Predicate, isNegated: Boolean = false, vararg args: Value): Fluent =
            FluentImpl(instanceOf, isNegated, listOf(*args))

        /**
         * Method for the creation of a positive [Effect].
         */
        fun positive(instanceOf: Predicate, vararg args: Value): Fluent =
            FluentImpl(instanceOf, false, listOf(*args))

        /**
         * Method for the creation of a negative [Fluent].
         */
        fun negative(instanceOf: Predicate, vararg args: Value): Fluent =
            FluentImpl(instanceOf, true, listOf(*args))
    }
}
