package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.domainDSL

class ParametersDSLTest : AnnotationSpec() {
    private fun removePostfix(string: String) = string.replace("_[0-9]".toRegex(), "")

    @Test
    fun parametersDSLworksAsExpected() {
        val parameters = domainDSL.actions.first().parameters
        val X = parameters[TestUtils.Values.X]
        if (X != null) {
            removePostfix(X.name) shouldBe removePostfix(TestUtils.Values.X.name)
            X.superType shouldBe TestUtils.Types.blocks
        }

        val Y = parameters[TestUtils.Values.Y]
        if (Y != null) {
            removePostfix(Y.name) shouldBe removePostfix(TestUtils.Values.Y.name)
            Y.superType shouldBe TestUtils.Types.blocks
        }

    }
}
