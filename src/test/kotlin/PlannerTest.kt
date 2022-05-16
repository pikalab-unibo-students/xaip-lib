import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.TestUtils.planEmpty
import resources.TestUtils.planNotEmpty
import resources.TestUtils.planner
import resources.TestUtils.problemEmpty
import resources.TestUtils.problemNotEmpty

class PlannerTest {
    @Test
    fun testEmptyCreation(){
        //planner.plan(problemEmpty) shouldBe planEmpty
    }
    @Test
    fun testNotEmptyCreation(){
        //planner.plan(problemNotEmpty) shouldBe planNotEmpty
    }
}