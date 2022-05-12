import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkClass
import org.junit.Test
import resources.Res.state

class GoalTest {
    private val goalSatisfied: Goal= mockkClass(Goal::class){
        every { isSatisfiedBy(state) } returns true
    }
    private val goalNotSatisfied: Goal= mockkClass(Goal::class){
        every { isSatisfiedBy(state) } returns false
    }

    @Test
    fun testCreation1(){
        goalSatisfied.isSatisfiedBy(state) shouldBe true
    }
    @Test
    fun testCreation2(){
        goalNotSatisfied.isSatisfiedBy(state) shouldBe false
    }


}