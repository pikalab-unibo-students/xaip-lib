package core.impl

import core.Object
import core.VariableAssignment
import it.unibo.tuprolog.core.Constant

internal data class ObjectImpl(internal val delegate: Constant) : Object {
    override val representation: String
        get() = delegate.value.toString()

    override fun apply(substitution: VariableAssignment): Object = this

    override val isGround: Boolean
        get() = true

    override fun refresh(scope: Context): Object = this

    override fun toString(): String = delegate.toString()
}
