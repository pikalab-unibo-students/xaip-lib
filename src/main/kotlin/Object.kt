import impl.ObjectImpl
import it.unibo.tuprolog.core.Atom
//TODO scrivi qualcosa di sensato
interface Object : Value {
    val representation: String
    override fun apply(substitution: VariableAssignment): Object

    companion object {
        fun of(delegate: Atom): Object = ObjectImpl(delegate)
    }
}