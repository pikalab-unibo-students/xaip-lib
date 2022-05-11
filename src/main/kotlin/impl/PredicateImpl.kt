package impl

import Predicate
import Type

class PredicateImpl(
    override val name: String,
    override val arguments: List<Type>) : Predicate