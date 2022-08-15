package dsl

import Predicate
import Type
import dsl.provider.TypeProvider

/**
 * Class representing a [Predicate] in the DSL.
 */
class PredicatesDSL(private val typesProvider: TypeProvider) {
    val predicates = mutableSetOf<Predicate>()
    /**
     * */
    operator fun Predicate.unaryPlus() {
        if (typeExist(this.arguments)) predicates += this
    }

    private fun typeExist(types: Iterable<Type>): Boolean {
        for (type in types)
            if (typesProvider.findType(type.name) == null) error("Type non found: ${type.name}")
        return true
    }

    private fun typeConverter(vararg types: String): List<Type> {
        val typesList = mutableListOf<Type>()
        for (type in types) {
            val supertype = typesProvider.findType(type)?.superType
            typesList.add(Type.of(type, supertype))
        }
        return typesList
    }

    /**
     * Method that can be called on any instances of the class without a method name.
     */
    operator fun String.invoke(vararg types: String): Predicate {
        val typesList = typeConverter(*types)
        if (typeExist(typesList)) {
            return Predicate.of(this, typesList)
        } else {
            error("This should never happen")
        }
    }
}
