package dsl

import Action
import Effect
import Fluent
import Type
import Variable

/**
 * Class representing a [Action] in the DSL.
 */
class ActionDSL(
    private val predicateProvider: PredicateProvider
) {
    var parameters: Map<Variable, Type> = mapOf()
    var effects: MutableSet<Effect> = mutableSetOf()
    var preconditions: MutableSet<Fluent> = mutableSetOf()

    /**
     * Method responsible for the creation of the action.
     */
    fun toAction(name: String): Action = Action.of(name, parameters, preconditions, effects)
    // TODO("FIXA È SBAGLIATO MA DETEKT SE NO  NON COMPILA")

    /**
     * Method responsible for the creation of the action's parameters.
     */
    fun parameters(f: ParametersDSL.() -> Unit) {
        parameters = ParametersDSL().also(f).parameters
    }

    /**
     * Method responsible for the creation of the action's preconditions.
     */
    fun preconditions(f: FluentDSL.() -> Unit) {
        preconditions += FluentDSL(predicateProvider).also(f).fluents
    }

    /**
     * Method responsible for the creation of the action's effects.
     */
    fun effects(f: EffectsDSL.() -> Unit) {
        // effects += effectsDSL().also(f).effects
    }
}
