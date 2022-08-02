package dsl

import Predicate
import Type

/**
 * Class representing a [Predicate] in the DSL.
 */
class PredicateDSL {
    val predicates = mutableSetOf<Predicate>()

    /**
     * Method that updates the internal list of [predicates] adding the last one created.
     */
    operator fun Predicate.unaryPlus() {
        // it il poverino non sa da dove prenderlo; mi sta bene l'utilizzo dell'overloading sugli operatori unari ma
        // questo it dovrà pur saltare fuori da qualche parte
        predicates.add(this)
    }

    /**
     * Method that allow to treat a [String] as it was a [Predicate].
     */
    operator fun String.invoke(vararg types: Type): Predicate = Predicate.of(this, *types)
}
