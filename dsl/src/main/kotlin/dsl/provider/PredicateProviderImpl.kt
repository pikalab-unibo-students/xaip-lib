package dsl.provider

import core.Predicate

/**
 * Class representing an instance of [PredicateProvider].
 */
class PredicateProviderImpl(private val predicateProvider: () -> Iterable<Predicate>) : PredicateProvider {
    private val predicates: Map<Pair<String, Int>, Predicate>
        get() = predicateProvider().associateBy { it.name to it.arguments.size }

    override fun findPredicate(name: String, arity: Int): Predicate? {
        return predicates[name to arity]
    }
}
