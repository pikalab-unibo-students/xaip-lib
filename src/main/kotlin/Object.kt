import impl.ObjectImpl
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Real

//TODO scrivi qualcosa di sensato
interface Object : Value {
    val representation: String
    override fun apply(substitution: VariableAssignment): Object

    companion object {
        @Deprecated("Così esponi il dettaglio tecnico che dietro le quinte usi 2P-Kt")
        fun of(delegate: Atom): Object = ObjectImpl(delegate)

        fun of(value: String): Object = ObjectImpl(Atom.of(value))

        fun of(value: Long): Object = ObjectImpl(Integer.of(value))

        fun of(value: Int): Object = ObjectImpl(Integer.of(value))

        fun of(value: Double): Object = ObjectImpl(Real.of(value))
    }
}