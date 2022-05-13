import impl.VarImpl

//TODO scrivi qualcosa di sensato
interface Var: Value {
    val name: String


    companion object {
        fun of(name: String): Var = VarImpl(name)
    }
}