package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.domainDSL

class ParametersDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun parametersDSLworksAsExpected() {
        val parameters = domainDSL.actions.first().parameters
        parameters shouldBe mapOf()
    }
}
