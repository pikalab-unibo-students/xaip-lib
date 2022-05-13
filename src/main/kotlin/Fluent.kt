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

    fun match(other: Fluent): Boolean
    fun mgu(other: Fluent): Substitution
}