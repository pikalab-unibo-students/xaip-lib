package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.domainDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class ParametersDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun parametersDSLworksAsExpected() {
        val parameters = domainDSL.actions.first().parameters
        parameters shouldBe mapOf()
    }
}
