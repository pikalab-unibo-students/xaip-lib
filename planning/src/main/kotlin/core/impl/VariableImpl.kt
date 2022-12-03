package core.impl

import core.Value
import core.Variable
import core.VariableAssignment
import core.impl.res.toLogic
import core.impl.res.toScope
import core.impl.res.toTerm
import core.impl.res.toValue
import it.unibo.tuprolog.core.Var as LogicVar

internal data class VariableImpl(internal val delegate: LogicVar) : Variable {

    constructor(name: String) : this(LogicVar.of(name))

    override val name: String
        get() = delegate.completeName

    override val isGround: Boolean
        get() = false

    override fun apply(substitution: VariableAssignment): Value =
        toTerm().apply(substitution.toLogic()).toValue()

    override fun refresh(scope: Context): Variable =
        VariableImpl(delegate.freshCopy(scope.toScope()))

    override fun toString(): String = delegate.toString()
}
