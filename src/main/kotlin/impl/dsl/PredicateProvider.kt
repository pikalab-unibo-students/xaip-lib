package impl.dsl

import Predicate

interface PredicateProvider {
    fun findPredicate(name: String, arity: Int): Predicate?
}

class PredicateProvideImpl(predicates: Iterable<Predicate>) : PredicateProvider {
    private val predicates: Map<Pair<String, Int>, Predicate> =
        predicates.associateBy { it.name to it.arguments.size }

    override fun findPredicate(name: String, arity: Int): Predicate? {
        return predicates[name to arity]
    }
}

class PredicateProvideImpl2(private val domain: DomainDSL) : PredicateProvider {
    private val predicates: Map<Pair<String, Int>, Predicate>
        get() = domain.predicates.associateBy { it.name to it.arguments.size }

    override fun findPredicate(name: String, arity: Int): Predicate? {
        return predicates[name to arity]
    }
}
