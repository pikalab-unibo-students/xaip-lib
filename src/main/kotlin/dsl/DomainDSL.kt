package dsl

import Action
import Axiom
import Domain
import Predicate
import Type
import dsl.provider.PredicateProvider
import dsl.provider.TypeProvider

/**
 * Class representing a [Domain] in the DSL.
 */
class DomainDSL {
    var name: String = "" // tolto lateinit
    var predicates: Set<Predicate> = emptySet()
    var actions: Set<Action> = emptySet()
    var types: Set<Type> = emptySet()
    var axiom: Axiom? = null

    private var predicateProvider = PredicateProvider.of(this)
    private var typesProvider = TypeProvider.of(this)

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
        val actionDSL = ActionsDSL(predicateProvider, typesProvider)
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
        val axiomsDSL = AxiomDSL(predicateProvider, typesProvider)
        axiomsDSL.f()
        this.axiom = axiomsDSL.toAxiom()
    }

    /**
     *  Method responsible that build an instance of [DomainDSL] and converts it to a [Domain].
     */
    fun buildDomain(): Domain =
        Domain.of(name, predicates.toSet(), actions.toSet(), types.toSet(), axiom)
}

/**
 * Entry point for [DomainDSL] creation.
 */
fun domain(f: DomainDSL.() -> Unit): Domain {
    return DomainDSL().also(f).buildDomain()
}
