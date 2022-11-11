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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DomainImpl

        if (name != other.name) return false
        if (predicates != other.predicates) return false
        if (actions != other.actions) return false
        if (types != other.types) return false
        if (axioms != other.axioms) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + predicates.hashCode()
        result = 31 * result + actions.hashCode()
        result = 31 * result + types.hashCode()
        result = 31 * result + (axioms?.hashCode() ?: 0)
        return result
    }
}
