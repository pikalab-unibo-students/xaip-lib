import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test

class FluentBasedGoalTest{

    private val fluentBasedGoalEmpty: FluentBasedGoal= mockkClass(FluentBasedGoal::class){
        every { fluent } returns emptySet()
    }

    private val fluentBasedGoalNotEmpty: FluentBasedGoal= mockkClass(FluentBasedGoal::class){
        every { fluent } returns mockk(relaxed=true)
    }
    @Test
    fun testEmptyCreation(){
        fluentBasedGoalEmpty.fluent.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmptyCreation(){
        fluentBasedGoalNotEmpty.fluent.isEmpty() shouldBe false
    }

}
