package impl

import Substitution
import Value
import Var
import impl.res.toTerm
import impl.res.toValue
import it.unibo.tuprolog.core.Substitution as LogicSubstitution

class SubstitutionImpl(internal val delegate: LogicSubstitution) : Substitution {

    override val keys: Set<Var>
        get() = delegate.keys.map { it.toValue() }.toSet()

    override val size: Int
        get() = delegate.size

    override val values: Collection<Value>
        get() = delegate.values.map { it.toValue() }

    override fun containsKey(key: Var): Boolean = key.toTerm() in delegate.keys
//Di qui erano da finire
    override fun containsValue(value: Value): Boolean = value.toTerm() in delegate.values

    override fun get(key: Var): Value = delegate.getValue(key.toTerm()).toValue()

    override fun isEmpty(): Boolean = delegate.isEmpty()

// Non ho capito cosa io debba fare
    override fun merge(other: Substitution): Substitution {
        TODO("Not yet implemented")
    }
//  TODO: fixa questa porcheria; quell'entry non so perché ci sia
    override val entries: Set<Map.Entry<Var, Value>>
        get() = delegate.entries.map {
            mapOf(it.key.toValue() to it.value.toValue())
        }.toSet() as Set<Map.Entry<Var, Value>>
}