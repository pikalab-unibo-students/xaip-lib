package core.impl

import core.Action
import core.Axiom
import core.Domain
import core.Predicate
import core.Type

internal data class DomainImpl(
    override val name: String,
    override val predicates: Set<Predicate>,
    override val actions: Set<Action>,
    override val types: Set<Type>,
    override val axioms: Axiom?
) : Domain {
    override fun toString(): String =

        """${Domain::class.simpleName}(
            |  ${Domain::name.name}=$name,
            |  ${Domain::predicates.name}=$predicates,
            |  ${Domain::actions.name}=$actions,
            |  ${Domain::types.name}=$types,
            |  ${Domain::axioms.name}=$axioms,
            |)
        """.trimMargin()
}
