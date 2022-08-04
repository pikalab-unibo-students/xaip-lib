package dsl.provider

import Predicate
import dsl.DomainDSL

/**
 * Class representing an istance of [PredicateProvider].
 */
class PredicateProviderImpl(private val domain: DomainDSL) : PredicateProvider {
    private val predicates: Map<Pair<String, Int>, Predicate>
        get() = domain.predicates.associateBy { it.name to it.arguments.size }

    override fun findPredicate(name: String, arity: Int): Predicate? {
        return predicates[name to arity]
    }
}
