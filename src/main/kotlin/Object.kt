import impl.ObjectImpl
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Real
import it.unibo.tuprolog.core.Scope

//TODO scrivi qualcosa di sensato
interface Object : Value {
    val representation: String

    override fun apply(substitution: VariableAssignment): Object

    override fun refresh(scope: Scope): Object

    companion object {

        fun of(value: String): Object = ObjectImpl(Atom.of(value))

        fun of(value: Long): Object = ObjectImpl(Integer.of(value))

        fun of(value: Int): Object = ObjectImpl(Integer.of(value))

        fun of(value: Double): Object = ObjectImpl(Real.of(value))
    }
}