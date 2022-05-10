/**
 * A state is represented as a conjunction of [fluents] that are ground,
 * functionless atoms TODO controlla sta cosa.
 *
 */
interface State {
    val fluents:Set<Fluent>
    fun apply(action: Action): State
    fun isApplicable(action: Action): Boolean
}


class StateImpl(override val fluents: Set<Fluent>) : State {
    override fun apply(action: Action): State {
        TODO("Not yet implemented")
    }

    override fun isApplicable(action: Action): Boolean {
        TODO("Not yet implemented")
    }
}
