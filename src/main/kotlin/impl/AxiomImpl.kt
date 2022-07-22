package impl

import Axiom
import Fluent
import Type
import Variable

internal data class AxiomImpl(
    override val parameters: Map<Variable, Type>,
    override val context: Set<Fluent>,
    override val implies: Set<Fluent>
) : Axiom {
    //serve progettare un po' la disgiunzione (OR) e congiunzione (AND) dei predicati nella testa e nella cosa degli axiom

    override fun toString(): String =
        "forall " + parameters.entries.joinToString(", ") { (v, t) -> "$v in $t" } + " : " +
                context.joinToString(" /\\") + " -> " + implies.joinToString(" /\\")
}