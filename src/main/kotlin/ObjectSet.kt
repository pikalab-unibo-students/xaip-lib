import impl.ObjectSetImpl

interface ObjectSet  {
    val map : Map<Type, Set<Object>>

    companion object {
        fun of(map : Map<Type, Set<Object>>): ObjectSet = ObjectSetImpl(map)
    }
}