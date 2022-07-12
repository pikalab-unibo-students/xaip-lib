import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.Planners
import resources.TestUtils.Problems

class PlannerTest : AnnotationSpec() {

    @Ignore
    @Test
    fun testPlannerObjectWorksAsExpected() {
        Planners.dummyPlanner.plan(Problems.stackAny)
    }

    @Test
    fun testPlanner() {
        val generatedPlan=Planners.dummyPlanner.plan(Problems.stack).first()
        val plan2check= Plan.of(listOf(TestUtils.Actions.pick.apply(VariableAssignment.of(TestUtils.Values.X, TestUtils.Values.c))))

        generatedPlan shouldBe plan2check
    }
}