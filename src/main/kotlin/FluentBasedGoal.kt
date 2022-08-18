import impl.FluentBasedGoalImpl
/**
 * A [FluentBasedGoal] is the entity representing the [Goal] of a [Problem].
 *
 * @property targets: set of fluents present in the [Goal] definitions. */
interface FluentBasedGoal : Goal, Applicable<FluentBasedGoal> {
    val targets: Set<Fluent>

    companion object {
        /***
         * Factory method for an [FluentBasedGoal] creation.
         */
        fun of(fluents: Set<Fluent>): FluentBasedGoal = FluentBasedGoalImpl(fluents)

        /***
         * Factory method for an [FluentBasedGoal] creation.
         */
        fun of(vararg fluents: Fluent): FluentBasedGoal = of(setOf(*fluents))
    }
}
