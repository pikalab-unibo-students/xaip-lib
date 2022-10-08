import impl.StateImpl

/**
 * A state is represented as a conjunction of [fluents] that are ground,
 * functionless atoms.
 *
 */
interface State {
    /**
     *
     */
    val fluents: Set<Fluent>

    /**
     * Method that compute the application of an [Operator] to the current [State].
     * @return a sequence of [State] reachable from the current one given the application of an [Operator].
     */
    fun apply(action: Operator): Sequence<State>

    /**
     * Method that checks if an [Action] can be applied to the current [State].
     */
    fun isApplicable(action: Action): Boolean

    companion object {
        /**
         * Factory method for an [State] creation.
         */
        fun of(fluents: Set<Fluent>): State = StateImpl(fluents)

        /**
         * Factory method for an [State] creation.
         */
        fun of(vararg fluents: Fluent): State = of(setOf(*fluents))
    }
}
