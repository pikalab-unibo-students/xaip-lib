package impl.res

import Value
import Var
import Object
import Substitution
import impl.ObjectImpl
import impl.SubstitutionImpl
import impl.VarImpl
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.Var as LogicVar
import it.unibo.tuprolog.core.Substitution as LogicSubstitution

internal fun Term.toValue(): Value = when (this) {
    is LogicVar -> toValue()
    is Atom -> toValue()
    else -> error("Cannot convert ${this::class} into ${Value::class}")
}

internal fun LogicVar.toValue(): Var = VarImpl(this)

internal fun Atom.toValue(): Object = ObjectImpl(this)

internal fun Value.toTerm(): Term = when (this) {
    is Var -> toTerm()
    is Object -> toTerm()
    else -> error("Cannot convert ${this::class} into ${Term::class}")
}

internal fun LogicSubstitution.toPddl(): Substitution = SubstitutionImpl(this)

internal fun Var.toTerm(): LogicVar =
    (this as? VarImpl)?.delegate ?: error("Cannot convert ${this::class} into ${LogicVar::class}")

internal fun Object.toTerm(): Atom =
    (this as? ObjectImpl)?.delegate ?: error("Cannot convert ${this::class} into ${Atom::class}")

internal fun Substitution.toLogic(): LogicSubstitution =
    (this as? SubstitutionImpl)?.delegate ?: error("Cannot convert ${this::class} into ${Substitution::class}")
