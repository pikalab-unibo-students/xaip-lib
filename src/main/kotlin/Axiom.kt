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
