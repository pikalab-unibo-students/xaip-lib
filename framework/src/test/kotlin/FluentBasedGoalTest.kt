import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Goals

class FluentBasedGoalTest : AnnotationSpec() {
    private val atXarm = FluentBasedGoal.of(BlockWorldDomain.Fluents.atXArm)

    @Test
    fun testFluentBasedGoalObjectWorksAsExpected() {
        Goals.pickX shouldBe atXarm
        Goals.pickX.targets.isEmpty() shouldNotBe true
        Goals.pickX.isSatisfiedBy(BlockWorldDomain.States.atAArm) shouldBe true
        Goals.pickX.targets.first() shouldBe BlockWorldDomain.Fluents.atXArm
        Goals.pickX.targets shouldBe atXarm.targets
        Goals.pickX.targets shouldNotBe atXarm.refresh().targets
    }

    @Test
    fun testRefreshMethodWorksAsExpected() {
        Goals.pickX.targets shouldBe atXarm.targets
        Goals.pickX.targets shouldNotBe atXarm.refresh().targets
    }

    @Test
    fun testApplyMethodWorksAsExpected() {
        Goals.pickX shouldBe atXarm
        Goals.pickX.targets.first().args.first() shouldBe BlockWorldDomain.Values.X
        val goalUpdated = Goals.pickX.apply(BlockWorldDomain.VariableAssignments.x2a)
        goalUpdated.targets.first().args.first() shouldBe BlockWorldDomain.Values.a
    }
}
