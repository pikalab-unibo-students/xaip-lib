import impl.VariableImpl
import it.unibo.tuprolog.core.Scope

// scrivi qualcosa di sensato
interface Variable : Value {
    val name: String

    override fun refresh(scope: Scope): Variable

    companion object {
        fun of(name: String): Variable = VariableImpl(name)
    }
}
