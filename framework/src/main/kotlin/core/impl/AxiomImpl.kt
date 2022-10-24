package core.impl

import core.Axiom
import core.Expression
import core.Type
import core.Variable

internal data class AxiomImpl(
    override val parameters: Map<Variable, Type>,
    override val context: Expression,
    override val implies: Expression
) : Axiom {
    override fun toString(): String =
        "forall " + parameters.entries.joinToString(", ") { (v, t) -> "$v in $t" } + " : " +
            context + " -> " + implies
}
