import io.kotest.matchers.shouldBe
import kotlin.test.Test
import resources.TestUtils.planEmpty
import resources.TestUtils.planNotEmpty
import resources.TestUtils.planner
import resources.TestUtils.problemEmpty
import resources.TestUtils.problemNotEmpty
import kotlin.test.Ignore

class PlannerTest {
    @Test
    @Ignore
    fun testEmptyCreation(){
        planner.plan(problemEmpty) shouldBe planEmpty
    }

    @Test
    @Ignore
    fun testNotEmptyCreation(){
        planner.plan(problemNotEmpty) shouldBe planNotEmpty
    }
}