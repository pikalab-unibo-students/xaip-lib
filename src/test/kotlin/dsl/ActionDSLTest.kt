package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Actions
import resources.TestUtils.Domains.blockWorld

class ActionDSLTest : AnnotationSpec() {
    @Test
    fun actionsDSLworksAsExpected() {
        blockWorld.actions.size shouldBe 3
        blockWorld.actions.first().name shouldBe Actions.pick.name
        blockWorld.actions.first().parameters.size shouldBe 1
        blockWorld.actions.first().preconditions.size shouldBe 3
        blockWorld.actions.first().effects.size shouldBe 4
    }
}
