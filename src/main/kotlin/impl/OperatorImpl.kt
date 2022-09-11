package impl

import Effect
import Fluent
import Operator
import Type
import Value
import Variable
import VariableAssignment
import it.unibo.tuprolog.core.Scope

internal data class OperatorImpl(
    override val name: String,
    override val parameters: Map<Variable, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>,
    override val positiveEffects: Set<Effect>,
    override val negativeEffects: Set<Effect>,
    override val args: List<Value>
) : Operator {
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
     * Mainly used to avoid spurrious substitutions.
     */
    override fun refresh(scope: Scope): Operator {
        return copy(
            // parameters = parameters.mapKeys { (k,_)-> k.refresh(scope) },
            preconditions = preconditions.map { it.refresh(scope) }.toSet(),
            effects = effects.map { it.refresh(scope) }.toSet(),
            args = args.map { it.refresh(scope) }
        )
    }

    override fun toString(): String = "$name(${args.joinToString(", ") {it.toString()}})"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OperatorImpl

        if (name != other.name) return false
        if (parameters != other.parameters) return false
        if (preconditions != other.preconditions) return false
        if (effects != other.effects) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + preconditions.hashCode()
        result = 31 * result + effects.hashCode()
        return result
    }
}
