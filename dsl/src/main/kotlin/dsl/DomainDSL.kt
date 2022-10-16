package dsl

import core.Action
import core.Axiom
import core.Domain
import core.Predicate
import core.Type
import dsl.provider.PredicateProvider
import dsl.provider.TypeProvider

/**
 * Class representing a [Domain] in the DSL.
 */
class DomainDSL {
    /**
     * @property name: represents the name of the [Domain].
     */
    var name: String = ""

    /**
     * @property predicates: set of [Predicate] of the [Domain].
     */
    var predicates: Set<Predicate> = emptySet()

    /**
     * @property actions: set of [Action] of the [Domain].
     */
    var actions: Set<Action> = emptySet()

    /**
     * @property types: set of [Type] of the [Domain].
     */
    var types: Set<Type> = emptySet()

    /**
     * @property axiom: represents the [Axiom] of the domain.
     */
    var axiom: Axiom? = null

    private var predicateProvider = PredicateProvider.of(this)
    private var typesProvider = TypeProvider.of(this)

    /**
     * Method that allows to call [PredicatesDSL] methods in an instance of a [DomainDSL] without any qualifiers.
     */
    fun predicates(f: PredicatesDSL.() -> Unit) {
        val predicatesDSL = PredicatesDSL(typesProvider)
        predicatesDSL.f()
        this.predicates = predicatesDSL.predicates
    }

    /**
     * Method that allows to call [ActionsDSL] methods in an instance of a [DomainDSL] without any qualifiers.
     */
    fun actions(f: ActionsDSL.() -> Unit) {
        val actionDSL = ActionsDSL(predicateProvider, typesProvider)
        actionDSL.f()
        this.actions = actionDSL.actions
    }

    /**
     * Method that allows to call [TypesDSL] methods in an instance of a [DomainDSL] without any qualifiers.
     */
    fun types(f: TypesDSL.() -> Unit) {
        val typesDSL = TypesDSL()
        typesDSL.f()
        this.types = typesDSL.types
    }

    /**
     * Method that allows to call [AxiomDSL] methods in an instance of a [DomainDSL] without any qualifiers.
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
