import impl.FluentBasedGoalImpl
/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface FluentBasedGoal : Goal, Applicable<FluentBasedGoal> {
    val targets: Set<Fluent>

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(fluents: Set<Fluent>): FluentBasedGoal = FluentBasedGoalImpl(fluents)
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(vararg fluents: Fluent): FluentBasedGoal = of(setOf(*fluents))
    }
}
