package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Values
import resources.TestUtils.domainDSL
import resources.TestUtils.removePostfix

class ParametersDSLTest : AnnotationSpec() {
    @Test
    fun parametersDSLworksAsExpected() {
        val parameters = domainDSL.actions.first().parameters
        removePostfix(Values.X.name) shouldBe removePostfix(parameters.keys.first().name)
        Values.X.isGround shouldBe parameters.keys.first().isGround
        removePostfix(Values.Y.name) shouldBe removePostfix(parameters.keys.last().name)
        Values.Y.isGround shouldBe parameters.keys.last().isGround
    }
}
