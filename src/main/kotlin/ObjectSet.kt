import impl.ObjectSetImpl
/**
 * Entity that wraps a set of [Object] and their [Type].
 * @property map: variable that contains the set of [Object] and their [Type].
 */
interface ObjectSet {
    var map: Map<Type, Set<Object>>

    companion object {
        /**
         * Factory method for an [ObjectSet] creation.
         */
        fun of(map: Map<Type, Set<Object>>): ObjectSet = ObjectSetImpl(map)

        /**
         * Factory method for an [ObjectSet] creation.
         */
        fun of(vararg map: Pair<Type, Set<Object>>): ObjectSet = of(map.toMap())
    }
}
