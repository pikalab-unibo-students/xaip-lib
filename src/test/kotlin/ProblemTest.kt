import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Problems
import resources.TestUtils.Domains
import resources.TestUtils.ObjectSets
import resources.TestUtils.States
import resources.TestUtils.Goals

class ProblemTest : AnnotationSpec() {


    @Test
    fun testProblemObjectWorksAsExpected() {
        Problems.stackAny.domain shouldBe Domains.blockWorld
        Problems.stackAny.objects shouldBe ObjectSets.all
        Problems.stackAny.initialState shouldBe States.initial
        Problems.stackAny.goal shouldBe Goals.atXArmAndAtYFloorAndOnWZ
    }
}