package dsl

import Effect
import Fluent
import dsl.provider.PredicateProvider

/**
 * */
class EffectsDSL(
    predicateProvider: PredicateProvider
) : AbstractFluentDSL(predicateProvider) {
    var effects: MutableSet<Effect> = mutableSetOf()

    /**
     * */
    operator fun Fluent.unaryPlus() = effects.add(Effect.positive(this))

    /**
     * */
    operator fun String.unaryPlus() = effects.add(Effect.positive(this()))

    /**
     * */
    operator fun Fluent.unaryMinus() = effects.add(Effect.negative(this))

    /**
     * */
    operator fun String.unaryMinus() = effects.add(Effect.negative(this()))
}