package dsl.provider

import Type

/**
 * */
class TypeProviderImpl(private val typesProvider: () -> Iterable<Type>) : TypesProvider {
    private val types: Map<String, Type>
        get() = typesProvider().associateBy { it.name }

    /**
     * */
    override fun findProvider(name: String): Type? {
        return types[name]
    }
}
