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

    private var preconditions: MutableSet<Fluent> = mutableSetOf()

    var effects: Set<Effect> = setOf()

    /**
     * Method responsible for the creation of the action.
     */
    fun toAction(name: String): Action = Action.of(name, parameters, preconditions, effects)
    // TODO("FIXA È SBAGLIATO MA DETEKT SE NO  NON COMPILA")

    /**
     * Method responsible for the creation of the action's parameters.
     */
    fun params(f: ParametersDSL.() -> Unit) {
        parameters = ParametersDSL().also(f).parameters
    }

    /**
     * Method responsible for the creation of the action's preconditions.
     */
    fun preconditions(f: FluentDSL.() -> Unit) {
        preconditions += FluentDSL(predicateProvider).also(f).fluents
    }
}
