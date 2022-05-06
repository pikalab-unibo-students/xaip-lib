import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test

class GoalTest {
    val state= mockk<State>()
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