import impl.FluentImpl
import impl.TypeImpl

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
    fun mostGeneralUnifier(other: Fluent): Substitution
    companion object {
        fun of(name: String,
               args: List<Value>,
               instanceOf: Predicate,
               isNegated: Boolean
        ): Fluent = FluentImpl(name, args, instanceOf, isNegated)
    }
}