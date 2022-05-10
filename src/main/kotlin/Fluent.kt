/**
 * A fluent represents a predicate ([instanceOf]) at runtime.
 * It states its truthiness/falseness.
 */
interface Fluent {
    val name: String
    val args: List<Value>
    val instanceOf: Predicate
    val isNegated: Boolean
}

class FluentImpl(
    override val name: String,
    override val args: List<Value>,
    override val instanceOf: Predicate,
    override val isNegated: Boolean
) : Fluent