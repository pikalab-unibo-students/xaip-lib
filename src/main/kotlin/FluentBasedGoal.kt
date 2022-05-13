import impl.FluentBasedGoalImpl

//TODO: partorire un nome meno orrido; riguarda/richiedi il significato.
/**
 *
 */
interface FluentBasedGoal : Goal, Applicable<FluentBasedGoal> {
    val fluent: Set<Fluent>
    companion object {
        fun of(fluent: Set<Fluent>): FluentBasedGoal = FluentBasedGoalImpl(fluent)
    }
}

