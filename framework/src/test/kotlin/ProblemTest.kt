import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.Domains
import resources.domain.BlockWorldDomain.Goals
import resources.domain.BlockWorldDomain.ObjectSets
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.States

class ProblemTest : AnnotationSpec() {

    @Test
    fun testProblemObjectWorksAsExpected() {
        Problems.stackAny.domain shouldBe Domains.blockWorld
        Problems.stackAny.objects shouldBe ObjectSets.all
        Problems.stackAny.initialState shouldBe States.initial
        Problems.stackAny.goal shouldBe Goals.atXArmAndAtYFloorAndOnWZ
    }
}
