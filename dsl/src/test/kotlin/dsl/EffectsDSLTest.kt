package dsl
import domain.BlockWorldDomain.Domains.blockWorld
import domain.BlockWorldDomain.Predicates
import domain.BlockWorldDomain.predicates
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe

class EffectsDSLTest : AnnotationSpec() {
    @Test
    fun effectsDSLworksAsExpected() {
        val effects = blockWorld.actions.first().effects
        effects.first().fluent.name shouldBe Predicates.at.name
        val effectNames = effects.map { it.fluent.name }
        val predicateNames = predicates.map { it.name }
        effectNames.map { it shouldBeIn predicateNames }
    }
}
