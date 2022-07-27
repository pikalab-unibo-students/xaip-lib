package impl

import Effect
import Fluent
import VariableAssignment
import it.unibo.tuprolog.core.Scope

internal data class EffectImpl(
    override val fluent: Fluent,
    override val isPositive: Boolean
) : Effect {

    override fun apply(substitution: VariableAssignment): Effect =
        copy(fluent = fluent.apply(substitution))

    override fun refresh(scope: Scope): Effect {
        return copy(fluent = fluent.refresh(scope))
    }
}
