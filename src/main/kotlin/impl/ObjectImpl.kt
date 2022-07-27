package impl

import Object
import VariableAssignment
import it.unibo.tuprolog.core.Constant
import it.unibo.tuprolog.core.Scope

internal data class ObjectImpl(internal val delegate: Constant) : Object {
    override val representation: String
        get() = delegate.value.toString()

    override fun apply(substitution: VariableAssignment): Object = this

    override val isGround: Boolean
        get() = true

    override fun refresh(scope: Scope): Object = this

    override fun toString(): String = delegate.toString()
}
