package dsl

import domain.BlockWorldDomain.Domains.blockWorld
import domain.BlockWorldDomain.Values
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class ParametersDSLTest : AnnotationSpec() {
    @Test
    fun parametersDSLworksAsExpected() {
        val parameters = blockWorld.actions.first().parameters
        Values.X.name.filter { it.isUpperCase() } shouldBe parameters.keys.first().name.filter { it.isUpperCase() }
        Values.X.isGround shouldBe parameters.keys.first().isGround
        Values.X.name.filter { it.isUpperCase() } shouldBe parameters.keys.last().name.filter { it.isUpperCase() }
        Values.Y.isGround shouldBe parameters.keys.last().isGround
    }
}
