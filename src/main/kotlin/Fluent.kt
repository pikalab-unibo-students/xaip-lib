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

    operator fun not(): Fluent

    fun match(other: Fluent): Boolean
    fun mostGeneralUnifier(other: Fluent): VariableAssignment

    companion object {
        @Deprecated("No need to provide a name: it can be inferred from the predicate")
        fun of(
            name: String,
            args: List<Value>,
            instanceOf: Predicate,
            isNegated: Boolean
        ): Fluent {
            require(name == instanceOf.name)
            return FluentImpl(instanceOf, isNegated, args)
        }

        fun of(instanceOf: Predicate, isNegated: Boolean = false, args: List<Value>): Fluent =
            FluentImpl(instanceOf, isNegated, args)

        fun of(instanceOf: Predicate, isNegated: Boolean = false, vararg args: Value): Fluent =
            FluentImpl(instanceOf, isNegated, listOf(*args))

        fun positive(instanceOf: Predicate, vararg args: Value): Fluent =
            FluentImpl(instanceOf, false, listOf(*args))

        fun negative(instanceOf: Predicate, vararg args: Value): Fluent =
            FluentImpl(instanceOf, true, listOf(*args))
    }
}