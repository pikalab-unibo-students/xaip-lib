import impl.ActionImpl

/**
 * An [Action] represents a way for changing the state of the world.
 *
 * @property name: states the name of the action.
 * @property parameters: is a map of variables, (and their types) on which the particular rule operates.
 * @property preconditions: is a goal description that must be satisfied before the action is applied.
 * @property effects: describe the effects of the action.
 * */
interface Action /*: Applicable<Action>*/ {
    val name: String
    val parameters: Map<Variable, Type>
    val preconditions: Set<Fluent>
    val effects: Set<Effect>
    val positiveEffects: Set<Effect>
    val negativeEffects: Set<Effect>

    companion object {
        /**
         * Factory method for an [Action] creation.
         */
        fun of(
            name: String,
            parameters: Map<Variable, Type>,
            preconditions: Set<Fluent>,
            effects: Set<Effect>
        ): Action = ActionImpl(name, parameters, preconditions, effects)
    }
}
