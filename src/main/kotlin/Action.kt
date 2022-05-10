/**
 * An action represents a way for changing the state of the world.
 *
 * The [name] states the name of the action.
 * The [parameters] is a map of variables, (and their types) on which the particular rule operates.
 * The [preconditions] is a goal description that must be satisfied before the action is applied.
 * The [effects] describe the effects of the action.
 * */
interface Action {
    val name: String
    val parameters: Map<Var, Type>
    val preconditions: Set<Fluent>
    val effects: Set<Effect>
}

// TODO Chiedi se ha senso tenere tutto come val oppure se dovrebbero essere var
class ActionImpl(override val name: String, override val parameters: Map<Var, Type>, override val preconditions: Set<Fluent>, override val effects: Set<Effect>): Action



