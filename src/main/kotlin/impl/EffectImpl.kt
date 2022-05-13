package impl

import Effect
import Fluent

data class EffectImpl(
    override val fluent: Fluent,
    override val isPositive: Boolean) : Effect