import impl.ActionImpl

/**
 * An action represents a way for changing the state of the world.
 *
 * The [name] states the name of the action.
 * The [parameters] is a map of variables, (and their types) on which the particular rule operates.
 * The [preconditions] is a goal description that must be satisfied before the action is applied.
 * The [effects] describe the effects of the action.
 * */
interface Action : Applicable<Action> {
    val name: String
    val parameters: Map<Variable, Type>
    val preconditions: Set<Fluent>
    val effects: Set<Effect>

    companion object {
        fun of(name: String,
               parameters: Map<Variable, Type>,
               preconditions: Set<Fluent>,
               effects: Set<Effect>
        ): Action = ActionImpl(name, parameters, preconditions, effects)
    }
}





