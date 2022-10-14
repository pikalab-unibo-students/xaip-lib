package impl

import Fluent
import NotUnifiableException
import Predicate
import Value
import VariableAssignment
import impl.res.toPddl
import impl.res.toTerm
import it.unibo.tuprolog.unify.Unificator
import it.unibo.tuprolog.core.Substitution as LogicSubstitution

internal data class FluentImpl(
    override val instanceOf: Predicate,
    override val isNegated: Boolean,
    override val args: List<Value>
) : Fluent {

    init {
        require(args.size == instanceOf.arguments.size) {
            "An instance of predicate $instanceOf should be provided with exactly ${instanceOf.arguments.size} " +
                "arguments, while ${args.size} were actually provided: $args"
        }
    }

    override fun negate(): Fluent = copy(isNegated = !isNegated)

    override val name: String
        get() = instanceOf.name

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

    override fun refresh(scope: Context): Fluent =
        copy(args = args.map { it.refresh(scope) })

    override fun toString(): String = (if (isNegated) "~" else "") + this.toTerm().toString()
}
