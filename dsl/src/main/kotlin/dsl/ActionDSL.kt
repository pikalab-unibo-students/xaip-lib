package dsl

import Action
import Effect
import Fluent
import Type
import Variable
import dsl.provider.PredicateProvider
import dsl.provider.TypeProvider
import dsl.provider.VariableProvider

/**
 * Class representing a [Action] in the DSL.
 */
class ActionDSL(
    private val predicateProvider: PredicateProvider,
    private val typeProvider: TypeProvider
) {
    /**
     * @property parameters: map matching each variable with the corresponding type.
     */
    var parameters: Map<Variable, Type> = mapOf()

    /**
     * @property effects: set representing the action's effect.
     */
    var effects: MutableSet<Effect> = mutableSetOf()

    /**
     * @property preconditions: set representing the action's preconditions.
     */
    var preconditions: MutableSet<Fluent> = mutableSetOf()
    private val variableProvider: VariableProvider = VariableProvider.of()

    /**
     * Method responsible for the creation of the action's parameters.
     */
    fun parameters(f: ParametersDSL.() -> Unit) {
        parameters = ParametersDSL(variableProvider, typeProvider).also(f).parameters
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
        effects += EffectsDSL(predicateProvider).also(f).effects
    }

    /**
     * Method responsible for the creation of the [Action].
     */
    fun toAction(name: String): Action =
        Action.of(name, parameters.toMap(), preconditions.toSet(), effects.toSet())
}
