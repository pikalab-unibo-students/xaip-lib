package impl

import Fluent
import Predicate
import Substitution
import Value

data class FluentImpl(
    override val name: String,
    override val args: List<Value>,
    override val instanceOf: Predicate,
    override val isNegated: Boolean
) : Fluent {
    override val isGround: Boolean
        get() = args.all { it.isGround }

    override fun apply(substitution: Substitution): Fluent =
        copy(args = args.map { it.apply(substitution) })


}