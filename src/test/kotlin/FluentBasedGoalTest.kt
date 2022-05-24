import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import kotlin.test.Test
import resources.TestUtils.state

class FluentBasedGoalTest{

    private val fluentBasedGoalEmpty: FluentBasedGoal= mockkClass(FluentBasedGoal::class){
        every { fluents } returns emptySet()
        every { isSatisfiedBy(state) } returns false
    }

    private val fluentBasedGoalNotEmpty: FluentBasedGoal= mockkClass(FluentBasedGoal::class){
        every { fluents } returns mockk(relaxed=true)
        every { isSatisfiedBy(state) } returns true
    }
    @Test
    fun testEmptyCreation(){
        fluentBasedGoalEmpty.fluents.isEmpty() shouldBe true
        fluentBasedGoalEmpty.isSatisfiedBy(state) shouldBe false
    }
    @Test
    fun testNotEmptyCreation(){
        fluentBasedGoalNotEmpty.fluents.isEmpty() shouldBe false
        fluentBasedGoalNotEmpty.isSatisfiedBy(state) shouldBe true
    }

}
