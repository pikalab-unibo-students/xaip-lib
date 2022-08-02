package impl.dsl

import Predicate
import Type

class PredicateDSL {
    val predicates = mutableListOf<Predicate>()

    operator fun Predicate.unaryPlus() {
        // it il poverino non sa da dove prenderlo; mi sta bene l'utilizzo dell'overloading sugli operatori unari ma questo it dovrà pur saltare fuori da qualche parte
        // predicates.add(it)
    }

    operator fun String.invoke(vararg types: Type): Predicate = Predicate.of(this, *types)
}
