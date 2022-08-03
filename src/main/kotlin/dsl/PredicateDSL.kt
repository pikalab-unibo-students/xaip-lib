package dsl

import Predicate
import Type

class PredicateDSL(private val typesProvider: TypesProvider) {
    var predicates = mutableSetOf<Predicate>()

    /**
     * Method that updates the internal list of [predicates] adding the last one created.
     */
    operator fun Predicate.unaryPlus() {
        predicates += this
    }

    fun toPredicate(name: String, vararg arguments: Type): Predicate {
        for (type in arguments)
            if (typesProvider.findProvider(type.name) == null) error("Type do not exist")
        return Predicate.of(name, *arguments)
    }
}
