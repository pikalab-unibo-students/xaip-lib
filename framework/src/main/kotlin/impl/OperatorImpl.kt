package impl

import Effect
import Fluent
import Operator
import Type
import Value
import Variable
import VariableAssignment
internal data class OperatorImpl(
    override val name: String,
    override val parameters: Map<Variable, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>,
    override val args: List<Value> = parameters.keys.toList()
) : Operator {

    override val positiveEffects: Set<Effect> by lazy { effects.filter { it.isPositive }.toSet() }

    override val negativeEffects: Set<Effect> by lazy { effects.filterNot { it.isPositive }.toSet() }

    /**
     * Method responsible for the application of a logic substitution to the entity.
     */
    override fun apply(substitution: VariableAssignment): Operator =
        copy(
            preconditions = preconditions.map { it.apply(substitution) }.toSet(),
            effects = effects.map { it.apply(substitution) }.toSet(),
            args = args.map { it.apply(substitution) }
        )

    /**
     * Method responsible for the refreshing of variables.
     * Mainly used to avoid spurious substitutions.
     */
    override fun refresh(scope: Context): Operator {
        return copy(
            // parameters = parameters.mapKeys { (k,_)-> k.refresh(scope) },
            preconditions = preconditions.map { it.refresh(scope) }.toSet(),
            effects = effects.map { it.refresh(scope) }.toSet(),
            args = args.map { it.refresh(scope) }
        )
    }

    override fun toString(): String = "$name(${args.joinToString(", ") {it.toString()}})"
}
