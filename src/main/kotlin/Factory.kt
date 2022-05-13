import impl.VarImpl

object Factory {
    fun varOf(name: String): Var = VarImpl(name)

    fun objectOf(representation: String): Object

    fun objectOf(representation: Int): Object = objectOf(representation.toString())
}