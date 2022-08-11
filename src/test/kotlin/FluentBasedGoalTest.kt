import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils
import resources.TestUtils.Goals

class FluentBasedGoalTest : AnnotationSpec() {
    private val atXarm = FluentBasedGoal.of(TestUtils.Fluents.atXArm)

    @Test
    fun testFluentBasedGoalObjectWorksAsExpected() {
        Goals.pickX shouldBe atXarm
        Goals.pickX.targets.isEmpty() shouldNotBe true
        Goals.pickX.isSatisfiedBy(TestUtils.States.atAArm) shouldBe true
        Goals.pickX.targets.first() shouldBe TestUtils.Fluents.atXArm
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
        Goals.pickX.targets.first().args.first() shouldBe TestUtils.Values.X
        val goalUpdated = Goals.pickX.apply(TestUtils.VariableAssignments.x2a)
        goalUpdated.targets.first().args.first() shouldBe TestUtils.Values.a
    }
}
