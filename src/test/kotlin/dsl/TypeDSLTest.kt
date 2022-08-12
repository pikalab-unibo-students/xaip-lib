package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.Types
import resources.TestUtils.domainDSL

class TypeDSLTest : AnnotationSpec() {
    @Test
    fun typeDSLworksAsExpected() {
        domainDSL.types.size shouldBe 4
        Types.blocks shouldBeIn domainDSL.types
        Types.locations shouldBeIn domainDSL.types
    }
}
