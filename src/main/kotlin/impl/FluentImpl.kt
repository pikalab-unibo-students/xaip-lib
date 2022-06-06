package impl

import Fluent
import NotUnifiableException
import Predicate
import VariableAssignment
import Value
import impl.res.toPddl
import impl.res.toTerm
import it.unibo.tuprolog.unify.Unificator
import it.unibo.tuprolog.core.Substitution as LogicSubstitution

internal data class FluentImpl(
    override val name: String,
    override val args: List<Value>,
    override val instanceOf: Predicate,
    override val isNegated: Boolean
) : Fluent {

    override fun not(): Fluent = copy(isNegated = !isNegated)

    override val isGround: Boolean
        get() = args.all { it.isGround }

    override fun match(other: Fluent): Boolean =
        Unificator.default.match(this.toTerm(), other.toTerm())

    override fun mostGeneralUnifier(other: Fluent): VariableAssignment =
        when (val logicSubstitution = Unificator.default.mgu(this.toTerm(), other.toTerm())) {
            is LogicSubstitution.Unifier -> logicSubstitution.toPddl()
            else -> throw NotUnifiableException(this, other)
        }

    override fun apply(substitution: VariableAssignment): Fluent =
        copy(args = args.map { it.apply(substitution) })

    override fun toString(): String = (if (isNegated) "~" else "") + this.toTerm().toString()
}