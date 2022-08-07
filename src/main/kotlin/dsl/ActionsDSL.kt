package dsl

import Action
import Effect
import Type
import Variable
import dsl.provider.PredicateProvider

/**
 * Class representing multiple actions in the DSL.
 */
class ActionsDSL(
    private val predicateProvider: PredicateProvider
) {
    val actions: MutableSet<Action> = mutableSetOf()

    /**
     * Method that allow to treat a [String] as it was a [Action].
     */
    operator fun String.invoke(f: ActionDSL.() -> Unit) {
        actions += ActionDSL(predicateProvider).also(f).toAction(this)
    }
}
