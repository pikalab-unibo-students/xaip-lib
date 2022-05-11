package resources

import Action
import Fluent
import Predicate
import State
import impl.*
import io.mockk.mockk

object res {
    const val nameGC ="Giovanni"
    val predicate= mockk<Predicate>()
    val state= mockk<State>()
    val action = mockk<Action>()

    val value1 = ValueImpl()
    val type1 = TypeImpl()
    val variableEmpty = VarImpl("")
    val variableNotEmpty = VarImpl(nameGC)
    val predicateEmpty = PredicateImpl("", emptyList())
    val predicateNotEmpty = PredicateImpl(nameGC, listOf(type1))
    val fluentEmpty = FluentImpl("", emptyList(), predicateEmpty, false)
    val fluentNotEmpty = FluentImpl(nameGC, listOf(value1), predicateNotEmpty, true)
    val effectEmpty= EffectImpl(fluentEmpty, false)
    val effectNotEmpty= EffectImpl(fluentEmpty, true)
    val axiomEmpty = AxiomImpl(emptyMap(), emptySet(), emptySet())
    val axiomNotEmpty = AxiomImpl(mapOf(variableNotEmpty to type1 ), setOf(fluentNotEmpty), setOf(fluentNotEmpty))
    val actionEmpty= ActionImpl("", emptyMap(), emptySet(), emptySet())
    val actionNotEmpty =ActionImpl(nameGC, mapOf(variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(
        effectNotEmpty))
}