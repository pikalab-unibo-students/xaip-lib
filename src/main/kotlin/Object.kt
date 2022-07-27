import impl.ObjectImpl
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Real
import it.unibo.tuprolog.core.Scope

/***
 * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
 */
interface Object : Value {
    val representation: String

    override fun apply(substitution: VariableAssignment): Object

    override fun refresh(scope: Scope): Object

    companion object {
        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(value: String): Object = ObjectImpl(Atom.of(value))

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(value: Long): Object = ObjectImpl(Integer.of(value))

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(value: Int): Object = ObjectImpl(Integer.of(value))

        /***
         * Scrivi qualcosa di sensato in futuro ora basta che passi il checkstyle.
         */
        fun of(value: Double): Object = ObjectImpl(Real.of(value))
    }
}
