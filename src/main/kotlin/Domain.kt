import impl.DomainImpl

/**
 * The [Domain] definition establishes the context of the world.
 * It determines what sorts of details the states can include.
 * @property name: name of the [Domain].
 * @property predicates: facts allowed in the problem.
 * @property actions: set of actions allowed to move among states.
 * @property types: types user can use for the objects' definition.
 * @property axioms: set of rules to be applied to the problem.
 */
interface Domain {
    val name: String
    val predicates: Set<Predicate>
    val actions: Set<Action>
    val types: Set<Type>
    val axioms: Set<Axiom>

    companion object {
        /***
         * Factory method for an [Domain] creation.
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
