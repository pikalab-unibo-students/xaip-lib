package dsl.provider

import core.Domain
import core.Predicate
import dsl.DomainDSL

/**
 * Provider of the predicates created in the instance of [DomainDSL].
 */
interface PredicateProvider {
    /**
     * Method that given a [name] of a [Predicate] and its [arity]
     * retrieves it if it exists and returns null otherwise.
     */
    fun findPredicate(name: String, arity: Int): Predicate?
    companion object {
        /**
         * Factory method for an [PredicateProvider] creation from a [DomainDSL].
         */
        fun of(
            domain: DomainDSL
        ): PredicateProvider = PredicateProviderImpl { domain.predicates }

        /**
         * Factory method for an [PredicateProvider] creation from a [Domain].
         */
        fun of(
            domain: Domain
        ): PredicateProvider = PredicateProviderImpl { domain.predicates }

        /**
         * Factory method for an [PredicateProvider] creation from a set of [Predicate]s.
         */
        fun of(
            predicates: Set<Predicate>
        ): PredicateProvider = PredicateProviderImpl { predicates }
    }
}
