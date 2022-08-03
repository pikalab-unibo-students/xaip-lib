package dsl

import Type

class TypeProviderImpl(private val domain: DomainDSL) : TypesProvider {
    private val types: Map<String, Type>
        get() = domain.types.associateBy { it.name }

    override fun findProvider(name: String): Type? {
        return types[name]
    }
}
