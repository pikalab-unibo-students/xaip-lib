package impl.res

import Fluent
import Value
import Variable
import Object
import Predicate
import VariableAssignment
import impl.ObjectImpl
import impl.VariableAssignmentImpl
import impl.VariableImpl
import it.unibo.tuprolog.core.Atom
import it.unibo.tuprolog.core.Struct
import it.unibo.tuprolog.core.Term
import it.unibo.tuprolog.core.Var as LogicVar
import it.unibo.tuprolog.core.Substitution as LogicSubstitution

internal fun Term.toValue(): Value = when (this) {
    is LogicVar -> toValue()
    is Atom -> toValue()
    else -> error("Cannot convert ${this::class} into ${Value::class}")
}

internal fun LogicVar.toValue(): Variable = VariableImpl(this)

internal fun Atom.toValue(): Object = ObjectImpl(this)

internal fun Value.toTerm(): Term = when (this) {
    is Variable -> toTerm()
    is Object -> toTerm()
    else -> error("Cannot convert ${this::class} into ${Term::class}")
}

internal fun LogicSubstitution.toPddl(): VariableAssignment = VariableAssignmentImpl(this)

private val negationFunctors = setOf("not", "\\+")

internal fun Struct.toFluent(instanceOf: Predicate): Fluent =
    when {
        arity == 1 && functor in negationFunctors && args[0] is Struct -> {
            (args[0] as Struct).toFluent(instanceOf).not()
        }
        arity == instanceOf.arguments.size && functor == instanceOf.name -> {
            Fluent.of(functor, args.map { it.toValue() }, instanceOf, false)
        }
        else -> {
            error("Cannot convert $this to ${Fluent::class.simpleName}: it is not matching $instanceOf")
        }
    }

internal fun Variable.toTerm(): LogicVar =
    (this as? VariableImpl)?.delegate ?: error("Cannot convert ${this::class} into ${LogicVar::class}")

internal fun Object.toTerm(): Atom =
    (this as? ObjectImpl)?.delegate ?: error("Cannot convert ${this::class} into ${Atom::class}")

internal fun VariableAssignment.toLogic(): LogicSubstitution =
    (this as? VariableAssignmentImpl)?.delegate ?: error("Cannot convert ${this::class} into ${VariableAssignment::class}")

internal fun Fluent.toTerm(): Struct = Struct.of(name, args.map { it.toTerm() })
