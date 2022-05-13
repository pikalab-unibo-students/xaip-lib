package impl

import Predicate
import Type

internal data class PredicateImpl(
    override val name: String,
    override val arguments: List<Type>) : Predicate