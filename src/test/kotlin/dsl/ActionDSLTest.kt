package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Actions
import resources.TestUtils.domainDSL

class ActionDSLTest : AnnotationSpec() {
    @Test
    fun actionsDSLworksAsExpected() {
        domainDSL.actions.size shouldBe 1
        domainDSL.actions.first().name shouldBe Actions.stack.name
        domainDSL.actions.first().parameters.size shouldBe 2
        domainDSL.actions.first().preconditions.size shouldBe 2
        domainDSL.actions.first().effects.size shouldBe 4
    }
}
