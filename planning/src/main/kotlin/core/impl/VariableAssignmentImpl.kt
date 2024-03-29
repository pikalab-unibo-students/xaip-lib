package core.impl

import core.Value
import core.Variable
import core.VariableAssignment
import core.impl.res.toLogic
import core.impl.res.toPddl
import core.impl.res.toTerm
import core.impl.res.toValue
import java.util.* // ktlint-disable no-wildcard-imports
import it.unibo.tuprolog.core.Substitution as LogicSubstitution

/**
 * Implementation class for the [VariableAssignment] interface.
 */
data class VariableAssignmentImpl(internal val delegate: LogicSubstitution) : VariableAssignment {

    constructor(variable: Variable, value: Value) : this(LogicSubstitution.of(variable.toTerm(), value.toTerm()))

    override val keys: Set<Variable>
        get() = delegate.keys.map { it.toValue() }.toSet()

    override val size: Int
        get() = delegate.size

    override val values: Collection<Value>
        get() = delegate.values.map { it.toValue() }

    override fun containsKey(key: Variable): Boolean = key.toTerm() in delegate.keys

    override fun containsValue(value: Value): Boolean = value.toTerm() in delegate.values

    override fun get(key: Variable): Value = delegate.getValue(key.toTerm()).toValue()

    override fun isEmpty(): Boolean = delegate.isEmpty()

    override fun merge(other: VariableAssignment): VariableAssignment =
        (this.toLogic() + other.toLogic()).toPddl()

    override val entries: Set<Map.Entry<Variable, Value>> =
        delegate.entries.map { (k, v) -> AbstractMap.SimpleEntry(k.toValue(), v.toValue()) }.toSet()
}
