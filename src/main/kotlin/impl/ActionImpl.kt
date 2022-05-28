package impl

import Action
import Effect
import Fluent
import VariableAssignment
import Type
import Variable

/**
 * An action is a quartet ⟨N, M, P, D⟩; where N is the action name, P is a map of variables and their types,
 * P is a set of fluents representing pre-conditions,
 * E is a set of effect, representing positive/negative post-conditions a.k.a. Add List or Remove List.
 */
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