package impl

import Substitution
import Value
import it.unibo.tuprolog.core.Var as LogicVar
import Variable
import impl.res.toLogic
import impl.res.toTerm
import impl.res.toValue

internal data class VariableImpl(internal val delegate: LogicVar) : Variable {

    constructor(name: String) : this(LogicVar.of(name))

    override val name: String
        get() = delegate.completeName

    override val isGround: Boolean
        get() = false

    override fun apply(substitution: Substitution): Value =
        toTerm().apply(substitution.toLogic()).toValue()
}