package dsl

import Predicate
import Type
import dsl.provider.TypesProvider

/**
 * Class representing a [Predicate] in the DSL.
 */
class PredicatesDSL(private val typesProvider: TypesProvider) {
    val predicates = mutableSetOf<Predicate>()

    /**
     *
     */
    operator fun Predicate.unaryPlus() { // stessa domanda di type, così funziona o cè bisogno di una String.invoke
        if (typeExist(this.arguments)) predicates += this
    }

    private fun typeExist(types: List<Type>): Boolean {
        for (type in types)
            if (typesProvider.findProvider(type.name) == null) error("type non found")
        return true
    }

    private fun typeConverter(vararg types: String): List<Type> {
        var typesList = mutableListOf<Type>()
        for (type in types) {
            typesList.add(Type.of(type))
        }
        return typesList
    }

    /**
     * Method that can be called on any instances of the class without a method name.
     */
    operator fun String.invoke(vararg types: String) {
        val typesList = typeConverter(*types)
        if (typeExist(typesList)) predicates += Predicate.of(this, typesList)
    }
}
