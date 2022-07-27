import impl.ObjectSetImpl

interface ObjectSet {
    val map: Map<Type, Set<Object>>

    companion object {

        fun of(map: Map<Type, Set<Object>>): ObjectSet = ObjectSetImpl(map)
        fun of(vararg map: Pair<Type, Set<Object>>): ObjectSet = of(map.toMap())
    }
}
