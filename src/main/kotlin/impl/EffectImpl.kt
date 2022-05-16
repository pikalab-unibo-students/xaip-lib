package impl

import Effect
import Fluent
import Substitution

internal data class EffectImpl(
    override val fluent: Fluent,
    override val isPositive: Boolean) : Effect {

    override fun apply(substitution: Substitution): Effect =
        copy(fluent = fluent.apply(substitution))
}