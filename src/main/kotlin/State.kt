/**
 * A state is represented as a conjunction of [fluents] that are ground,
 * functionless atoms TODO controlla sta cosa.
 *
 */
interface State : Applicable<State> {
    val fluents: Set<Fluent>
    fun apply(action: Action): State
    fun isApplicable(action: Action): Boolean
}
