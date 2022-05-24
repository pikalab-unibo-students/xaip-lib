package impl

import Substitution
import Value
import Var
import impl.res.toLogic
import impl.res.toPddl
import impl.res.toTerm
import impl.res.toValue
import java.util.AbstractMap
import it.unibo.tuprolog.core.Substitution as LogicSubstitution

class SubstitutionImpl(internal val delegate: LogicSubstitution) : Substitution {

    override val keys: Set<Var>
        get() = delegate.keys.map { it.toValue() }.toSet()

    override val size: Int
        get() = delegate.size

    override val values: Collection<Value>
        get() = delegate.values.map { it.toValue() }

    override fun containsKey(key: Var): Boolean = key.toTerm() in delegate.keys

    override fun containsValue(value: Value): Boolean = value.toTerm() in delegate.values

    override fun get(key: Var): Value = delegate.getValue(key.toTerm()).toValue()

    override fun isEmpty(): Boolean = delegate.isEmpty()

    override fun merge(other: Substitution): Substitution =
        (this.toLogic() + other.toLogic()).toPddl()

    override val entries: Set<Map.Entry<Var, Value>> =
        delegate.entries.map { (k, v) -> AbstractMap.SimpleEntry(k.toValue(), v.toValue()) }.toSet()
}