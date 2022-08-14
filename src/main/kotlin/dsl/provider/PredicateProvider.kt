package dsl.provider

import Domain
import Predicate
import dsl.DomainDSL

/**
 * Provider of the predicates created in the instance of [DomainDSL].
 */
interface PredicateProvider {
    /**
     * Method given a [name] of a [Predicate] and its [arity] it retrieves it if it exists and returns null otherwise.
     */
    fun findPredicate(name: String, arity: Int): Predicate?
    companion object {
        /***
         * Factory method for an [PredicateProvider] creation.
         */
        fun of(
            domain: DomainDSL
        ): PredicateProvider = PredicateProviderImpl { domain.predicates }

        /**
         * */
        fun of(
            domain: Domain
        ): PredicateProvider = PredicateProviderImpl { domain.predicates }

        /**
         * */
        fun of(
            predicates: Set<Predicate>
        ): PredicateProvider = PredicateProviderImpl { predicates }
    }
}
