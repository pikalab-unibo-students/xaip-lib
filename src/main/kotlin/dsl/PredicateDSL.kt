package dsl

import Predicate
import Type

class PredicateDSL(
    val types: List<Type>
) {
// non sono mica sicura che sta classe serva
    fun toPredicate(name: String): Predicate {
        return Predicate.of(name, types)
    }
}
