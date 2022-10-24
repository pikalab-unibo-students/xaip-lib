package core.impl

import core.Action
import core.Effect
import core.Fluent
import core.Type
import core.Variable

internal data class ActionImpl(
    override val name: String,
    override val parameters: Map<Variable, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>
) : Action {

    override val positiveEffects: Set<Effect> by lazy { effects.filter { it.isPositive }.toSet() }

    override val negativeEffects: Set<Effect> by lazy { effects.filterNot { it.isPositive }.toSet() }

    private fun Iterable<Fluent>.pretty(functor: String) =
        map { it.toString() }.sorted().joinToString(", ", "$functor(", ")")

    override fun toString(): String = "action($name, " +
        "${preconditions.pretty("if")}, " +
        "${positiveEffects.map { it.fluent }.pretty("addList")}, " +
        "${negativeEffects.map { it.fluent }.pretty("removeList")})"
}
