package dsl

import Predicate

/**
 * Class representing a [Predicate] in the DSL.
 */
class PredicatesDSL(private val typesProvider: TypesProvider) {
    val predicates = mutableSetOf<Predicate>()
    operator fun Predicate.unaryPlus() {
        predicates += this
    }

    /**
     * Method that allow to treat a [String] as it was a [Predicate].
     */
    // operator fun String.invoke(vararg types: Type): Predicate = Predicate.of(this, *types)
    operator fun String.invoke(f: PredicateDSL.() -> Unit) {
        predicates += PredicateDSL(typesProvider).also(f).toPredicate(this)
    }
}
