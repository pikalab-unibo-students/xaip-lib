//TODO: partorire un nome meno orrido; riguarda/richiedi il significato.
/**
 *
 */
interface FluentBasedGoal : Goal, Applicable<FluentBasedGoal> {
    val fluent: Set<Fluent>
}

