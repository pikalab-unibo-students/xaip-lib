package dsl.provider

import Type

/**
 * */
class TypeProviderImpl(private val typesProvider: () -> Iterable<Type>) : TypeProvider {
    private val types: Map<String, Type>
        get() = typesProvider().associateBy { it.name }

    /**
     * */
    override fun findType(name: String): Type? {
        return types[name]
    }
}
