package dsl

import Action
import dsl.provider.PredicateProvider
import dsl.provider.TypeProvider

/**
 * Class representing multiple actions in the DSL.
 */
class ActionsDSL(
    private val predicateProvider: PredicateProvider,
    private val typeProvider: TypeProvider
) {
    val actions: MutableSet<Action> = mutableSetOf()

    /**
     * Method that allow to treat a [String] as it was a [Action].
     */
    operator fun String.invoke(f: ActionDSL.() -> Unit) {
        actions += ActionDSL(predicateProvider, typeProvider).also(f).toAction(this)
    }
}
