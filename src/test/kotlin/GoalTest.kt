import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.Res.goalNotSatisfied
import resources.Res.goalSatisfied
import resources.Res.state

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