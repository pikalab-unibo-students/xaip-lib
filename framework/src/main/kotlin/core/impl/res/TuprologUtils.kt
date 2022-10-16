package core.impl.res

import core.Fluent
import core.Object
import core.Predicate
import core.Value
import core.Variable
import core.VariableAssignment
import core.impl.* // ktlint-disable no-wildcard-imports
import core.impl.ObjectImpl
import core.impl.VariableImpl
import it.unibo.tuprolog.core.* // ktlint-disable no-wildcard-imports
import it.unibo.tuprolog.core.Substitution as LogicSubstitution
import it.unibo.tuprolog.core.Var as LogicVar

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
            (args[0] as Struct).toFluent(instanceOf).negate()
        }
        arity == instanceOf.arguments.size && functor == instanceOf.name -> {
            Fluent.of(instanceOf, false, args.map { it.toValue() })
        }
        else -> {
            error("Cannot convert $this to ${Fluent::class.simpleName}: it is not matching $instanceOf")
        }
    }

internal fun Variable.toTerm(): LogicVar =
    (this as? VariableImpl)?.delegate ?: error("Cannot convert ${this::class} into ${LogicVar::class}")

internal fun Context.toScope(): Scope =
    (this as? ContextImpl)?.delegate ?: error("Cannot convert ${this::class} into ${Scope::class}")

internal fun Object.toTerm(): Constant =
    (this as? ObjectImpl)?.delegate ?: error("Cannot convert ${this::class} into ${Constant::class}")

internal fun VariableAssignment.toLogic(): LogicSubstitution =
    (this as? VariableAssignmentImpl)?.delegate
        ?: error("Cannot convert ${this::class} into ${VariableAssignment::class}")

internal fun Fluent.toTerm(): Struct = Struct.of(name, args.map { it.toTerm() })
