class DomainTest {
    val domain = object : Domain{
        override val name: String
            get() = name
        override val predicates: Set<Predicate>
            get() = predicates
        override val actions: Set<Action>
            get() = actions
        override val types: Set<Type>
            get() = types
        override val axioms: Set<Axiom>
            get() = axioms
    }
}