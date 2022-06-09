import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.planEmpty
import resources.TestUtils.planNotEmpty
import resources.TestUtils.planner
import resources.TestUtils.problemEmpty
import resources.TestUtils.problemNotEmpty
import resources.TestUtils.Planners
import resources.TestUtils.Problems

class PlannerTest : AnnotationSpec() {

    @Test
    @Ignore
    fun testEmptyCreation() {
        planner.plan(problemEmpty) shouldBe planEmpty
    }

    @Test
    @Ignore
    fun testNotEmptyCreation() {
        planner.plan(problemNotEmpty) shouldBe planNotEmpty
    }

    @Ignore
    @Test
    fun testPlannerObjectWorksAsExpected() {
        Planners.dummyPlanner.plan(Problems.stackAny)
    }
}