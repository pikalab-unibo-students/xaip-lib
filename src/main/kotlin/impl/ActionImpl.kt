package impl

import Action
import Effect
import Fluent
import Type
import Value
import Variable
import VariableAssignment
import it.unibo.tuprolog.core.Scope

/**
 * An action is a quartet ⟨N, M, P, D⟩; where N is the action name, P is a map of variables and their types,
 * P is a set of fluents representing pre-conditions,
 * E is a set of effect, representing positive/negative post-conditions a.k.a. Add List or Remove List.
 */
internal data class ActionImpl(
    override val name: String,
    override val parameters: Map<Variable, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>,
    private val args: List<Value> = parameters.keys.toList()
) : Action {

    override fun apply(substitution: VariableAssignment): Action =
        copy(
            preconditions = preconditions.map { it.apply(substitution) }.toSet(),
            effects = effects.map { it.apply(substitution) }.toSet(),
            args = args.map { it.apply(substitution) }
        )

    override fun refresh(scope: Scope): Action {
        return copy(
            parameters = parameters.mapKeys { (k,_)-> k.refresh(scope) },
            preconditions = preconditions.map { it.refresh(scope) }.toSet(),
            effects = effects.map { it.refresh(scope) }.toSet(),
            args = args.map { it.refresh(scope) }
        )
    }

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

    override fun toString(): String = "$name(${args.joinToString(", ") {it.toString()}})"
}