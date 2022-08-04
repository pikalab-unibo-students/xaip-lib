package dsl

import Predicate
import Type
import dsl.provider.TypesProvider

/**
 * Class representing a [Predicate] in the DSL.
 */
class PredicatesDSL(private val typesProvider: TypesProvider) {
    val predicates = mutableSetOf<Predicate>()
    val types = listOf<Type>()

    /**
     *
     */
    operator fun Predicate.unaryPlus() { // stessa domanda di type, così funziona o cè bisogno di una String.invoke
        predicates += this
    }

    /**
     * Method that allow to treat a [String] as it was a [Predicate].
     */
    operator fun String.invoke(f: PredicateDSL.() -> Unit) {
        for (type in types)
            if (typesProvider.findProvider(type.name) == null) error("type non found")
        predicates += PredicateDSL(types).also(f).toPredicate(this)
    }
}
