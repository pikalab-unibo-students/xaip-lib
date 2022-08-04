package dsl

import Effect
import dsl.provider.PredicateProvider

class EffectDSL(
    private val predicateProvider: PredicateProvider
) {
    fun toEffect(): Effect {
        TODO()
    }
}
