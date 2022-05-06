interface Domain {
    val name: String
    val predicates: Set<Predicate>
    val actions: Set<Action>
    val types: Set<Type>
    val axioms: Set<Axiom>
}