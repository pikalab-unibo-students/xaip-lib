package dsl

import Effect
import Fluent
import kotlin.properties.Delegates

class EffectDSL(
    private val predicateProvider: PredicateProvider
) {
    lateinit var fluent: Fluent
    var isPositive by Delegates.notNull<Boolean>()

    fun toEffect(s: String): Effect {
        TODO()
    }
}
