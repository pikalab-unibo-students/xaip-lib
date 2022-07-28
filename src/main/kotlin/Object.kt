import impl.ObjectImpl
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Real
import it.unibo.tuprolog.core.Scope

/***
 * An [Object] represents the entities that can appear in the [Problem].
 */
interface Object : Value {
    val representation: String

    override fun apply(substitution: VariableAssignment): Object

    override fun refresh(scope: Scope): Object

    companion object {
        /***
         * Factory method for an [Object] creation.
         */
        fun of(value: String): Object = ObjectImpl(Atom.of(value))

        /***
         * Factory method for an [Object] creation.
         */
        fun of(value: Long): Object = ObjectImpl(Integer.of(value))

        /***
         * Factory method for an [Object] creation.
         */
        fun of(value: Int): Object = ObjectImpl(Integer.of(value))

        /***
         * Factory method for an [Object] creation.
         */
        fun of(value: Double): Object = ObjectImpl(Real.of(value))
    }
}
