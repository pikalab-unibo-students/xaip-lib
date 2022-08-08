package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.Types
import resources.TestUtils.domainDSL

class TypeDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun typeDSLworksAsExpected() {
        val typeDSL = domainDSL.types
        typeDSL.size shouldBe 2
        Types.blocks shouldBeIn typeDSL
        Types.locations shouldBeIn typeDSL
    }
}
