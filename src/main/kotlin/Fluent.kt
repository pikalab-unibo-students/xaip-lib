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