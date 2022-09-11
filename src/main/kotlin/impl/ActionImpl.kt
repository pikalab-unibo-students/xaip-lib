package impl

import Action
import Effect
import Fluent
import Type
import Variable

internal data class ActionImpl(
    override val name: String,
    override val parameters: Map<Variable, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>
    // private val args: List<Value> = parameters.keys.toList()
) : Action {
/*
    override fun apply(substitution: VariableAssignment): Action =
        copy(
            preconditions = preconditions.map { it.apply(substitution) }.toSet(),
            effects = effects.map { it.apply(substitution) }.toSet(),
            args = args.map { it.apply(substitution) }
        )

    override fun refresh(scope: Scope): Action {
        return copy(
            // parameters = parameters.mapKeys { (k,_)-> k.refresh(scope) },
            preconditions = preconditions.map { it.refresh(scope) }.toSet(),
            effects = effects.map { it.refresh(scope) }.toSet(),
            args = args.map { it.refresh(scope) }
        )
    }
*/
    override val positiveEffects: Set<Effect> by lazy { effects.filter { it.isPositive }.toSet() }

    override val negativeEffects: Set<Effect> by lazy { effects.filterNot { it.isPositive }.toSet() }

    private fun Iterable<Fluent>.pretty(functor: String) =
        map { it.toString() }.sorted().joinToString(", ", "$functor(", ")")

    @Suppress("unused")
    val descriptor: String
        get() = "action($name, " +
            "${preconditions.pretty("if")}, " +
            "${positiveEffects.map { it.fluent }.pretty("addList")}, " +
            "${negativeEffects.map { it.fluent }.pretty("removeList")})"

    // override fun toString(): String = "$name(${args.joinToString(", ") {it.toString()}})"
    /*
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActionImpl

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
    */
}
