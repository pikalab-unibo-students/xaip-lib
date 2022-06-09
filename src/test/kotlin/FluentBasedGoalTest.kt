import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import resources.TestUtils
import resources.TestUtils.state

class FluentBasedGoalTest : AnnotationSpec() {

    private val fluentBasedGoalEmpty: FluentBasedGoal = mockkClass(FluentBasedGoal::class) {
        every { targets } returns emptySet()
        every { isSatisfiedBy(state) } returns false
    }

    private val fluentBasedGoalNotEmpty: FluentBasedGoal = mockkClass(FluentBasedGoal::class) {
        every { targets } returns mockk(relaxed = true)
        every { isSatisfiedBy(state) } returns true
    }

    @Test
    fun testEmptyCreation() {
        fluentBasedGoalEmpty.targets.isEmpty() shouldBe true
        fluentBasedGoalEmpty.isSatisfiedBy(state) shouldBe false
    }

    @Test
    fun testNotEmptyCreation() {
        fluentBasedGoalNotEmpty.targets.isEmpty() shouldBe false
        fluentBasedGoalNotEmpty.isSatisfiedBy(state) shouldBe true
    }

    @Test
    fun testFluentBasedGoalObjectWorksAsExpected() {
        TestUtils.FluentBasedGoals.f1.targets.isEmpty() shouldNotBe true
        TestUtils.FluentBasedGoals.f1.isSatisfiedBy(TestUtils.States.atAArm) shouldBe true
    }

}
