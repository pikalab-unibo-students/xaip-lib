package impl.dsl

import Domain
import Predicate

/**
 * Class representing a [Domain] in the DSL.
 */
class DomainDSL {
    var name: String = "nome farlocco"
    var predicates: List<Predicate> = TODO()

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun predicates(f: PredicateDSL.() -> Unit) {
        val ps = PredicateDSL()

        ps.f()

        this.predicates = ps.predicates
    }

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun buildDomain(): Domain {
        TODO()
    }
}
