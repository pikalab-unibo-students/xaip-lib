package dsl
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.Predicates
import resources.TestUtils.domainDSL
import resources.TestUtils.predicates

class EffectsDSLTest : AnnotationSpec() {
    @Test
    fun effectsDSLworksAsExpected() {
        val effects = domainDSL.actions.first().effects
        effects.first().fluent.name shouldBe Predicates.on.name
        val effectNames = effects.map { it.fluent.name }
        val predicateNames = predicates.map { it.name }
        effectNames.map { it shouldBeIn predicateNames }
    }
}
