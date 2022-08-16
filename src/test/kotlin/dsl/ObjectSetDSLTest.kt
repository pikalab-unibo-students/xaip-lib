package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Types
import resources.TestUtils.problemDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class ObjectSetDSLTest : AnnotationSpec() {
    @Test
    fun typeDSLworksAsExpected() {
        val objectDSL = problemDSL.objects
        objectDSL.map.size shouldBe 1
        objectDSL.map.keys.first().name shouldBe Types.blocks.name
        objectDSL.map.keys.first().superType shouldBe Types.blocks.superType
    }
}
