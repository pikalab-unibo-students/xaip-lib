package dsl

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
    var predicates: Set<Predicate> = emptySet()
    var actions: Set<Action> = emptySet()
    var types: Set<Type> = emptySet()
    var axioms: Set<Axiom> = emptySet()
    private var predicateProvider = PredicateProvider.of(this)
    private var typesProvider = TypesProvider.of(this)
    /**Unit
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun predicates(f: PredicatesDSL.() -> Unit) {
        val predicatesDSL = PredicatesDSL(typesProvider)

        predicatesDSL.f()

        this.predicates = predicatesDSL.predicates
    }

    /**
     * Scrivi qualcosa di sensato quando fixi sta roba.
     */
    fun actions(f: ActionsDSL.() -> Unit) {
        val actionDSL = ActionsDSL(predicateProvider)

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
     *  Method responsible that build an instance of [DomainDSL] and converts it to a [Domain].
     */
    fun buildDomain(): Domain {
        TODO()
    }
}

/**
 * Entry point for [DomainDSL] creation.
 */
fun domain(f: DomainDSL.() -> Unit): Domain {
    return DomainDSL().also(f).buildDomain()
}
