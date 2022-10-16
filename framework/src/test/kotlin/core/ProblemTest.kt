package core

import domain.BlockWorldDomain.Domains
import domain.BlockWorldDomain.Goals
import domain.BlockWorldDomain.ObjectSets
import domain.BlockWorldDomain.Problems
import domain.BlockWorldDomain.States
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class ProblemTest : AnnotationSpec() {

    @Test
    fun testProblemObjectWorksAsExpected() {
        Problems.stackAny.domain shouldBe Domains.blockWorld
        Problems.stackAny.objects shouldBe ObjectSets.all
        Problems.stackAny.initialState shouldBe States.initial
        Problems.stackAny.goal shouldBe Goals.atXArmAndAtYFloorAndOnWZ
    }
}
