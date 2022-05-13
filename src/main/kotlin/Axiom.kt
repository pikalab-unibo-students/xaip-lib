import impl.AxiomImpl
import impl.VarImpl

/**
 * Axioms are logical formulas that assert relationships among propositions
 * that hold within a situation.
 *
 */
interface Axiom {
    val parameters: Map<Var, Type>
    val context: Set<Fluent>
    val implies: Set<Fluent>
    companion object {
        fun of(parameters: Map<Var, Type>,
               context: Set<Fluent>,
               implies: Set<Fluent>): Axiom = AxiomImpl(parameters, context, implies)
    }
}

