import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.BlockWorldDomain.Domains
import resources.BlockWorldDomain.Goals
import resources.BlockWorldDomain.ObjectSets
import resources.BlockWorldDomain.Problems
import resources.BlockWorldDomain.States

class ProblemTest : AnnotationSpec() {

    @Test
    fun testProblemObjectWorksAsExpected() {
        Problems.stackAny.domain shouldBe Domains.blockWorld
        Problems.stackAny.objects shouldBe ObjectSets.all
        Problems.stackAny.initialState shouldBe States.initial
        Problems.stackAny.goal shouldBe Goals.atXArmAndAtYFloorAndOnWZ
    }
}
