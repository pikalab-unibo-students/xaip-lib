package core

import core.impl.Context
import core.impl.ObjectImpl
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Integer
import it.unibo.tuprolog.core.Real

/**
 * An [Object] represents the entities that can appear in the [Problem].
 */
interface Object : Value {
    /**
     * @property representation: string representing the name of the object.
     */
    val representation: String

    override fun apply(substitution: VariableAssignment): Object

    override fun refresh(scope: Context): Object

    companion object {
        /**
         * Factory method for an [Object] creation.
         */
        fun of(value: String): Object = ObjectImpl(Atom.of(value))

        /**
         * Factory method for an [Object] creation.
         */
        fun of(value: Long): Object = ObjectImpl(Integer.of(value))

        /**
         * Factory method for an [Object] creation.
         */
        fun of(value: Int): Object = ObjectImpl(Integer.of(value))

        /**
         * Factory method for an [Object] creation.
         */
        fun of(value: Double): Object = ObjectImpl(Real.of(value))
    }
}
