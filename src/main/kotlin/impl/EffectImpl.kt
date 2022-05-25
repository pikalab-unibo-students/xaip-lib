package impl

import Effect
import Fluent
import VariableAssignment

internal data class EffectImpl(
    override val fluent: Fluent,
    override val isPositive: Boolean) : Effect {

    override fun apply(substitution: VariableAssignment): Effect =
        copy(fluent = fluent.apply(substitution))
}