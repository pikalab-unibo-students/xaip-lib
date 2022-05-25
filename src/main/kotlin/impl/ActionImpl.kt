package impl

import Action
import Effect
import Fluent
import VariableAssignment
import Type
import Variable

internal data class ActionImpl(
    override val name: String,
    override val parameters: Map<Variable, Type>,
    override val preconditions: Set<Fluent>,
    override val effects: Set<Effect>
): Action {
    override fun apply(substitution: VariableAssignment): Action =
        copy(
            preconditions = preconditions.map { it.apply(substitution) }.toSet(),
            effects = effects.map { it.apply(substitution) }.toSet()
        )
}