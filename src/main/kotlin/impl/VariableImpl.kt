package impl

import Value
import Variable
import VariableAssignment
import impl.res.toLogic
import impl.res.toTerm
import impl.res.toValue
import it.unibo.tuprolog.core.Scope
import it.unibo.tuprolog.core.Var as LogicVar

internal data class VariableImpl(internal val delegate: LogicVar) : Variable {

    constructor(name: String) : this(LogicVar.of(name))

    override val name: String
        get() = delegate.completeName

    override val isGround: Boolean
        get() = false

    override fun apply(substitution: VariableAssignment): Value =
        toTerm().apply(substitution.toLogic()).toValue()

    override fun refresh(scope: Scope): Variable =
        VariableImpl(delegate.freshCopy(scope))

    override fun toString(): String = delegate.toString()
}