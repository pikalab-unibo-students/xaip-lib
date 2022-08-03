package dsl

import Action
import Effect
import Type
import Variable

/**
 * Class representing multiple actions in the DSL.
 */
class ActionsDSL(
    private val predicateProvider: PredicateProvider
) {
    val actions: MutableSet<Action> = mutableSetOf()
    val parameters: Map<Variable, Type> = mutableMapOf()
    val effects: Set<Effect> = setOf()

    /**
     * Method that allow to treat a [String] as it was a [Action].
     */
    operator fun String.invoke(f: ActionDSL.() -> Unit) {
        actions += ActionDSL(predicateProvider).also(f).toAction(this)
        TODO("Capisci come aggiungere la roba anche parameter ed effect")
    }
}
