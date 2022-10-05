package dsl.provider

import Type
import dsl.DomainDSL

/**
 * Provider of the types created in the instance of [DomainDSL].
 */
interface TypeProvider {
    /**
     * Method that given a [name] of a [Type] retrieves it if it exists and returns null otherwise.
     */
    fun findType(name: String): Type?

    companion object {
        /***
         * Factory method for an [PredicateProvider] creation from a [DomainDSL].
         */
        fun of(domain: DomainDSL): TypeProvider = TypeProviderImpl { domain.types }

        /***
         * Factory method for an [PredicateProvider] creation from a set [Type]s.
         */
        fun of(types: Set<Type>): TypeProvider = TypeProviderImpl { types }
    }
}
