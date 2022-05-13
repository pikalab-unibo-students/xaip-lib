package impl

import Substitution
import Value
import it.unibo.tuprolog.core.Var as LogicVar
import Var

internal data class VarImpl(internal val delegate: LogicVar) : Var {

    constructor(name: String) : this(LogicVar.of(name))

    override val name: String
        get() = delegate.completeName

    override val isGround: Boolean
        get() = false

    override fun apply(substitution: Substitution): Value =
        toTerm().apply(substitution.toLogic()).toValue()
}