import impl.ObjectSetImpl
/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface ObjectSet {
    val map: Map<Type, Set<Object>>

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(map: Map<Type, Set<Object>>): ObjectSet = ObjectSetImpl(map)

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(vararg map: Pair<Type, Set<Object>>): ObjectSet = of(map.toMap())
    }
}
