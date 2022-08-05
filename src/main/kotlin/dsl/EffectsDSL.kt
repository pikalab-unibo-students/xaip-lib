package dsl

import Effect
import dsl.provider.PredicateProvider

/**
 * */
class EffectsDSL(
    private val predicateProvider: PredicateProvider
) {
    var effects: MutableSet<Effect> = mutableSetOf()

    var predicate: String = ""
    var arity: Int = 0
    var fluent: String = ""
    var isPositive = true

    /**
     * Method that allow to treat a [String] as it was a [Effect].
     */
    operator fun String.invoke() {
        if (predicateProvider.findPredicate(predicate, arity) == null) error("Predicate does not exist")
        effects += Effect.of(TODO())
    }
}
