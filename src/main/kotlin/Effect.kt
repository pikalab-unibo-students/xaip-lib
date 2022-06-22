import impl.EffectImpl

/**
 * An effect consists of a conjunctive logical expression ([fluent]],
 * which defines which values should be set to true or false ([isPositive])
 * if an action is applied.
 */
interface Effect : Applicable<Effect> {
    val fluent: Fluent
    val isPositive: Boolean

    fun match(other: Effect): Boolean = fluent.match(other.fluent)
    fun mostGeneralUnifier(other: Effect): VariableAssignment = fluent.mostGeneralUnifier(other.fluent)

    companion object {
        fun of(fluent: Fluent, isPositive: Boolean = true): Effect = EffectImpl(fluent, isPositive)
        fun positive(fluent: Fluent): Effect = EffectImpl(fluent, true)
        fun negative(fluent: Fluent): Effect = EffectImpl(fluent, false)
    }
}
