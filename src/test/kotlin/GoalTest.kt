import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.TestUtils.goalNotSatisfied
import resources.TestUtils.goalSatisfied
import resources.TestUtils.state

class GoalTest {

    @Test
    fun testCreation1(){
        goalSatisfied.isSatisfiedBy(state) shouldBe true
    }
    @Test
    fun testCreation2(){
        goalNotSatisfied.isSatisfiedBy(state) shouldBe false
    }


}