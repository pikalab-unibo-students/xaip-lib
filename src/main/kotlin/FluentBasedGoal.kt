import impl.FluentBasedGoalImpl

interface FluentBasedGoal : Goal, Applicable<FluentBasedGoal> {
    val targets: Set<Fluent>

    companion object {
        fun of(fluents: Set<Fluent>): FluentBasedGoal = FluentBasedGoalImpl(fluents)
        fun of(vararg fluents: Fluent): FluentBasedGoal = of(setOf(*fluents))
    }
}

