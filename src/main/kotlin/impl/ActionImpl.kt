package impl

import Action
import Effect
import Fluent
import Substitution
import Type
import Var

internal data class ActionImpl(
    override val name: String,
    override val parameters: Map<Var, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>
): Action {
    override fun apply(substitution: Substitution): Action =
        copy(
            preconditions = preconditions.map { it.apply(substitution) }.toSet(),
            effects = effects.map { it.apply(substitution) }.toSet()
        )
}