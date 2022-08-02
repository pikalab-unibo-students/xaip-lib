package impl.dsl

import Action
import Axiom
import Domain
import Predicate
import Type

/**
 * Class representing a [Domain] in the DSL.
 */
class DomainDSL {
    var name: String = "nome farlocco"
    var predicates: List<Predicate> = emptyList() // ma siamo sicuri che ci importi l'ordine?
    var actions: List<Action> = emptyList()
    var types: List<Type> = emptyList()
    var axioms: List<Axiom> = emptyList()

    /**Unit
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun predicates(f: PredicateDSL.() -> Unit) {
        val predicateDSL = PredicateDSL()

        predicateDSL.f()

        this.predicates = predicateDSL.predicates
    }

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun actions(f: ActionsDSL.() -> Unit) {
        val actionDSL = ActionsDSL()

        actionDSL.f()
        this.actions = actionDSL.actions
    }

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun types(f: TypeDSL.() -> Unit) {
        val typesDSL = TypeDSL()

        typesDSL.f()
        this.types = typesDSL.types
    }

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun axioms(f: AxiomDSL.() -> Unit) {
        val axiomsDSL = AxiomDSL()

        axiomsDSL.f()
        this.axioms = axiomsDSL.axioms
    }

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun buildDomain(): Domain {
        TODO()
    }
}

fun domain(f: DomainDSL.() -> Unit): Domain {
    return DomainDSL().also(f).buildDomain()
}

