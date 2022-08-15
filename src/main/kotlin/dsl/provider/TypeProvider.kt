package dsl.provider

import Type
import dsl.DomainDSL

/**
 * Provider of the types created in the instance of [DomainDSL].
 */
interface TypeProvider {
    /**
     * Method given a [name] of a [Type] retrieves it if it exists and returns null otherwise.
     */
    fun findType(name: String): Type?

    companion object {
        /***
         * Factory method for an [PredicateProvider] creation.
         */
        fun of(domain: DomainDSL): TypeProvider = TypeProviderImpl { domain.types }

        /**
         * */
        fun of(types: Set<Type>): TypeProvider = TypeProviderImpl { types }
    }
}
