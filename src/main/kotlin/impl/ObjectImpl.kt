package impl

import Object
import Substitution
import it.unibo.tuprolog.core.Atom

internal data class ObjectImpl(internal val delegate: Atom) : Object {
    override val representation: String
        get() = delegate.value

    override fun apply(substitution: Substitution): Object = this

    override val isGround: Boolean
        get() = true
}