package dsl

import Object
import ObjectSet
import Type
import dsl.provider.TypesProvider

/**
 *
 */
class ObjectSetDSL(private val typeProvider: TypesProvider) {
    var objectSet: ObjectSet = ObjectSet.of(emptyMap())
    var map = emptyMap<Type, Set<Object>>()

    /**
     *
     */
    operator fun Pair<Type, Set<Object>>.unaryPlus() {
        objectSet.map = objectSet.map.plus(this)
    }

    /**
     * */
    operator fun String.invoke(vararg args: String): Pair<Type, Set<Object>> {
        val type = typeProvider.findProvider(this)
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
