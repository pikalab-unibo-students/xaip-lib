package core.impl

import core.Effect
import core.Fluent
import core.VariableAssignment

internal data class EffectImpl(
    override val fluent: Fluent,
    override val isPositive: Boolean
) : Effect {

    override fun apply(substitution: VariableAssignment): Effect =
        copy(fluent = fluent.apply(substitution))

    override fun refresh(scope: Context): Effect {
        return copy(fluent = fluent.refresh(scope))
    }
}
