import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.planEmpty
import resources.TestUtils.planNotEmpty

class PlanTest {
    @Test
    fun testEmptyCreation(){
        planEmpty.actions.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmpty(){
        planNotEmpty.actions.isNotEmpty() shouldBe true
        planNotEmpty.actions.size shouldBe 1
        planNotEmpty.actions.forEach{it shouldBe actionNotEmpty}
    }
}