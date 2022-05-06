interface Action {
    val name: String
    val parameters: Map<Var, Type>
    val preconditions: Set<Fluent>
    val effects: Set<Effect>
}

