package impl

import Effect
import Fluent

class EffectImpl(
    override val fluent: Fluent,
    override val isPositive: Boolean) : Effect