package dsl

import Effect
import Fluent
import dsl.provider.PredicateProvider

/**
 * */
class EffectsDSL(
    predicateProvider: PredicateProvider
) : dsl.AbstractFluentDSL(predicateProvider) {
    var effects: MutableSet<Effect> = mutableSetOf()

    /**
     * Method that updates the internal list of [effects] adding the last positive fluent created.
     */
    operator fun Fluent.unaryPlus() = effects.add(Effect.positive(this))

    /**
     * Method that create a positive [Fluent] from a [String] without arguments.
     */
    operator fun String.unaryPlus() = effects.add(Effect.positive(this()))

    /**
     * Method that updates the internal list of [effects] adding the last negative fluent created.
     */
    operator fun Fluent.unaryMinus() = effects.add(Effect.negative(this))

    /**
     * Method that create a negative [Type] from a [String] without arguments.
     */
    operator fun String.unaryMinus() = effects.add(Effect.negative(this()))
}
