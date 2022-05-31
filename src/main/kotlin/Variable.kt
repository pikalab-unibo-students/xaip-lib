import impl.VariableImpl

//TODO scrivi qualcosa di sensato
interface Variable: Value {
    val name: String

    companion object {
        fun of(name: String): Variable = VariableImpl(name)
    }
}