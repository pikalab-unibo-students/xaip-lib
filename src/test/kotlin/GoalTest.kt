import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkClass
import org.junit.Test
import resources.res.state

class GoalTest {
    private val goalSatisfied: Goal= mockkClass(Goal::class){
        every { isStatisfiedBy(state) } returns true
    }
    private val goalNotSatisfied: Goal= mockkClass(Goal::class){
        every { isStatisfiedBy(state) } returns false
    }

    @Test
    fun testCreation1(){
        goalSatisfied.isStatisfiedBy(state) shouldBe true
    }
    @Test
    fun testCreation2(){
        goalNotSatisfied.isStatisfiedBy(state) shouldBe false
    }


}