package dsl

import Object
import ObjectSet
import Type
import dsl.provider.TypeProvider

/**
 *
 */
class ObjectSetDSL(private val typeProvider: TypeProvider) {
    var objectSet: ObjectSet = ObjectSet.of(emptyMap())
    var map = emptyMap<Type, Set<Object>>()

    /**
     * Method that updates the internal list of [objectSet] adding the last one created.
     */
    operator fun Pair<Type, Set<Object>>.unaryPlus() {
        objectSet.map = objectSet.map.plus(this)
    }

    /**
     * Method that invoked on a [String] creates a [Pair] from it and its arguments.
     */
    operator fun String.invoke(vararg args: String): Pair<Type, Set<Object>> {
        val type = typeProvider.findType(this)
        if (type != null) {
            return Pair(
                type,
                args.map {
                    Object.of(it)
                }.toSet()
            )
        } else {
            error("type not present in the Domain definition")
        }
    }
}
