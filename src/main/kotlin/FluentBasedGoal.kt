//TODO: partorire un nome meno orrido; riguarda/richiedi il significato.
/**
 *
 */
interface FluentBasedGoal: Goal {
    val fluent: Set<Fluent>
}

class FluentBasedGoalImpl(override val fluent: Set<Fluent>) : FluentBasedGoal {
    override fun isStatisfiedBy(state: State): Boolean {
        TODO("Not yet implemented")
    }
}