package impl

import Axiom
import Expression
import Type
import Variable

internal data class AxiomImpl(
    override val parameters: Map<Variable, Type>,
    override val context: Expression,
    override val implies: Expression
) : Axiom {
    override fun toString(): String =
        "forall " + parameters.entries.joinToString(", ") { (v, t) -> "$v in $t" } + " : " +
            context + " -> " + implies
}
