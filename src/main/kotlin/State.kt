import impl.StateImpl

/**
 * A state is represented as a conjunction of [fluents] that are ground,
 * functionless atoms TODO controlla sta cosa.
 *
 */
interface State {
    val fluents: Set<Fluent>

    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun apply(action: Action): Sequence<State>

    /***
     * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
     */
    fun isApplicable(action: Action): Boolean

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(fluents: Set<Fluent>): State = StateImpl(fluents)

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(vararg fluents: Fluent): State = of(setOf(*fluents))
    }
}
