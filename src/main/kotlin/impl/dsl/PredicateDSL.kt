package impl.dsl

import Predicate
import Type

/**
 * Class representing a [Predicate] in the DSL.
 */
class PredicateDSL {
    val predicates = mutableListOf<Predicate>()

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    operator fun Predicate.unaryPlus() {
        // it il poverino non sa da dove prenderlo; mi sta bene l'utilizzo dell'overloading sugli operatori unari ma
        // questo it dovrà pur saltare fuori da qualche parte
        predicates.add(this)
    }

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    operator fun String.invoke(vararg types: Type): Predicate = Predicate.of(this, *types)
}
