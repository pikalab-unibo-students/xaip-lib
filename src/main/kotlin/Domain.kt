import impl.DomainImpl

/**
 * The domain definition establishes the context of the world.
 * It determines what sorts of details the states can include.
 * These are: facts that are interested ([predicates]), and
 * what can we do to move between states in the world ([actions]),
 * [types] and [axioms].
 */
interface Domain {
    val name: String
    val predicates: Set<Predicate>
    val actions: Set<Action>
    val types: Set<Type>
    val axioms: Set<Axiom>

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(
            name: String,
            predicates: Set<Predicate>,
            actions: Set<Action>,
            types: Set<Type>,
            axioms: Set<Axiom>
        ): Domain = DomainImpl(name, predicates, actions, types, axioms)
    }
}
