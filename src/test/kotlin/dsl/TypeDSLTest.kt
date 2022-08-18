package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.Domains.blockWorld
import resources.TestUtils.Types

class TypeDSLTest : AnnotationSpec() {
    @Test
    fun typeDSLworksAsExpected() {
        blockWorld.types.size shouldBe 4
        Types.blocks shouldBeIn blockWorld.types
        Types.locations shouldBeIn blockWorld.types
    }
}
