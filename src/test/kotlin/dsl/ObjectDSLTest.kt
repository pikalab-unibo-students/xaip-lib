package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.domainDSL
import resources.TestUtils.Types

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class ObjectDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun typeDSLworksAsExpected() {
        val objectDSL = domainDSL.actions.first().preconditions
        objectDSL.size shouldBe 2
        Types.blocks shouldBeIn objectDSL
        Types.locations shouldBeIn objectDSL
    }
}
