package dsl

import Effect

class EffectsDSL(
    private val predicateProvider: PredicateProvider
) {
    var effects: MutableSet<Effect> = mutableSetOf()

    operator fun String.invoke(f: EffectDSL.() -> Unit) {
        effects += EffectDSL(predicateProvider).also(f).toEffect(this)
    }
}
