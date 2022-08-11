package dsl

import Object
import ObjectSet
import Type

class ObjectSetDSL {
    private lateinit var map: MutableMap<Type, Set<Object>>
    lateinit var objectSet: ObjectSet
    operator fun String.unaryPlus() {
        map[Type.of(this)] = emptySet()
    }

    operator fun String.invoke(vararg args: String) {
        map[Type.of(this)] = args.map {
            Object.of(it)
        }.toSet()
        objectSet = ObjectSet.of(map)
    }
}
