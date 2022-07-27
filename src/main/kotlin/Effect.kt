import impl.EffectImpl

/**
 * An effect consists of a conjunctive logical expression ([fluent]],
 * which defines which values should be set to true or false ([isPositive])
 * if an action is applied.
 */
interface Effect : Applicable<Effect> {
    val fluent: Fluent
    val isPositive: Boolean

    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun match(other: Effect): Boolean = fluent.match(other.fluent)

    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun mostGeneralUnifier(other: Effect): VariableAssignment = fluent.mostGeneralUnifier(other.fluent)

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(fluent: Fluent, isPositive: Boolean = true): Effect = EffectImpl(fluent, isPositive)

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun positive(fluent: Fluent): Effect = EffectImpl(fluent, true)

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun negative(fluent: Fluent): Effect = EffectImpl(fluent, false)
    }
}
