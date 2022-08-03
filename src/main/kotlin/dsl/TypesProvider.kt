package dsl

import Type

/**
 * Provider of the types created in the instance of [DomainDSL].
 */
interface TypesProvider {
    /**
     * Method given a [name] of a [Type] retrieves it if it exists and returns null otherwise.
     */
    fun findProvider(name: String): Type?

    companion object {
        /***
         * Factory method for an [PredicateProvider] creation.
         */
        fun of(domain: DomainDSL): TypesProvider = TypeProviderImpl(domain)
    }
}
