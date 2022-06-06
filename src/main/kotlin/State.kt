import impl.StateImpl
/**
 * A state is represented as a conjunction of [fluents] that are ground,
 * functionless atoms TODO controlla sta cosa.
 *
 */
interface State : Applicable<State> {
    val fluents: Set<Fluent>
    fun apply(action: Action): State
    fun isApplicable(action: Action): Boolean

    companion object {
        fun of(fluents: Set<Fluent>): State = StateImpl(fluents)
        fun of(vararg fluents: Fluent): State = of(setOf(*fluents))
    }
}
