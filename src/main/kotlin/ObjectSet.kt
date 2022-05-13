import impl.ObjectSetImpl

interface ObjectSet  {
    val map : Map<Type, Set<Object>>
    companion object {
        fun of(map : Map<Type, Set<Object>>): ObjectSet = ObjectSetImpl(map)
    }
}//cambiato le Constants non le abbiamo e un'interfaccia non può estendere da sta roba