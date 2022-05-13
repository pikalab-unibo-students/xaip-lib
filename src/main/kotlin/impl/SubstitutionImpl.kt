package impl

import Substitution
import Value
import Var
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Substitution as LogicSubstitution

class SubstitutionImpl(internal val delegate: LogicSubstitution) : Substitution {
    override fun merge(other: Substitution): Substitution {
        TODO("Not yet implemented")
    }

    override val entries: Set<Map.Entry<Var, Value>>
        get() = TODO("Not yet implemented")

    override val keys: Set<Var>
        get() = delegate.keys.map { it.toValue() }.toSet()

    override val size: Int
        get() = delegate.size

    override val values: Collection<Value>
        get() = delegate.values.map { it.toValue() }

    override fun containsKey(key: Var): Boolean = key.toTerm() in delegate.keys

    override fun containsValue(value: Value): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(key: Var): Value {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

}