import impl.AxiomImpl

/**
 * Axioms are logical formulas that assert relationships among propositions
 * that hold within a situation.
 * @property parameters: is the field that contains all the variables that appear in
 * the axiom along with their type.
 * @property context: rules stated by the [Axiom].
 * @property implies: consequences always true because of the [Axiom].
 * */
interface Axiom {
    val parameters: Map<Variable, Type>
    val context: Expression
    val implies: Expression

    companion object {
        /**
         * Factory method for an [Axiom] creation.
         */
        fun of(
            parameters: Map<Variable, Type>,
            context: Expression,
            implies: Expression
        ): Axiom = AxiomImpl(parameters, context, implies)
    }
}
