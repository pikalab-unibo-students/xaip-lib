import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils
import resources.TestUtils.FluentBasedGoals

class FluentBasedGoalTest : AnnotationSpec() {
    @Test
    fun testFluentBasedGoalObjectWorksAsExpected() {
        FluentBasedGoals.f1.targets.isEmpty() shouldNotBe true
        FluentBasedGoals.f1.isSatisfiedBy(TestUtils.States.atAArm) shouldBe true
    }
}
