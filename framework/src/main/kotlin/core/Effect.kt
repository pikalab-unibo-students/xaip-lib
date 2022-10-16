package core

import core.impl.EffectImpl

/**
 * An [Effect] is an entity that represents the result of the application of an [Action] to a given [State].
 */
interface Effect : Applicable<Effect> {
    /**
     *@property fluent: conjunctive logical expression.
     */
    val fluent: Fluent

    /**
     *@property isPositive: boolean field that state is it is a positive [Effect], or a negative one.
     */
    val isPositive: Boolean

    /**
     * Method that checks if two effects unify.
     * @return true if they do or, false otherwise.
     */
    fun match(other: Effect): Boolean = fluent.match(other.fluent)

    /**
     * Method that look for the most general unifier among two effects.
     * @return the mgu, if it finds one, or an exception if no mgu exists.
     */
    fun mostGeneralUnifier(other: Effect): VariableAssignment = fluent.mostGeneralUnifier(other.fluent)

    companion object {
        /**
         * Factory method for an [Effect] creation.
         * It creates a positive [Effect] by default.
         */
        fun of(fluent: Fluent, isPositive: Boolean = true): Effect = EffectImpl(fluent, isPositive)

        /**
         * Method for the creation of a positive [Effect].
         */
        fun positive(fluent: Fluent): Effect = EffectImpl(fluent, true)

        /**
         * Method for the creation of a negative [Effect].
         */
        fun negative(fluent: Fluent): Effect = EffectImpl(fluent, false)
    }
}
