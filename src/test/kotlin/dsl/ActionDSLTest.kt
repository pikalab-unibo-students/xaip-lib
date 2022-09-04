package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.Actions
import resources.domain.BlockWorldDomain.Domains.blockWorld

class ActionDSLTest : AnnotationSpec() {
    @Test
    fun actionsDSLworksAsExpected() {
        blockWorld.actions.size shouldBe 2 // tolto per il momento unstack
        blockWorld.actions.first().name shouldBe Actions.pick.name
        blockWorld.actions.first().parameters.size shouldBe 1
        blockWorld.actions.first().preconditions.size shouldBe 2
        blockWorld.actions.first().effects.size shouldBe 4
    }
}
