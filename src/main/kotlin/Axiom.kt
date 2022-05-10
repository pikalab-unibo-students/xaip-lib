/**
 * Axioms are logical formulas that assert relationships among propositions
 * that hold within a situation.
 *
 */
interface Axiom {
    val parameters: Map<Var, Type>
    val context: Set<Fluent>
    val implies: Set<Fluent>
}

class AxiomImpl(
    override val parameters: Map<Var, Type>,
    override val context: Set<Fluent>,
    override val implies: Set<Fluent>
):Axiom
