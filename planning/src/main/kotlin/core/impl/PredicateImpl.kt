package core.impl

import core.Predicate
import core.Type

internal data class PredicateImpl(override val name: String, override val arguments: List<Type>) : Predicate {
    override fun toString(): String =
        name + arguments.joinToString(", ", "(", ")")
}
