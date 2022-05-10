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
}

class DomainImpl(
    override val name: String,
    override val predicates: Set<Predicate>,
    override val actions: Set<Action>,
    override val types: Set<Type>,
    override val axioms: Set<Axiom>
) : Domain