package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.BlockWorldDomain.ProblemsDSL.problemOnAB
import resources.BlockWorldDomain.Types

class ObjectSetDSLTest : AnnotationSpec() {
    @Test
    fun typeDSLworksAsExpected() {
        val objectDSL = problemOnAB.objects
        objectDSL.map.size shouldBe 1
        objectDSL.map.keys.first().name shouldBe Types.blocks.name
        objectDSL.map.keys.first().superType shouldBe Types.blocks.superType
    }
}
