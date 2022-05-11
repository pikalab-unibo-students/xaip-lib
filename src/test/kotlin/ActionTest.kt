import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.res.actionEmpty
import resources.res.actionNotEmpty
import resources.res.nameGC
import kotlin.test.Test

class ActionTest {
    @Test
    fun testEmptyCreation() {
        actionEmpty.name shouldBe ""
        actionEmpty.parameters.isEmpty() shouldBe true
        actionEmpty.preconditions.isEmpty() shouldBe true
        actionEmpty.effects.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmptyCreation() {
        actionNotEmpty.name shouldBe nameGC
        actionNotEmpty.parameters.isEmpty() shouldNotBe true
        actionNotEmpty.preconditions.isEmpty() shouldNotBe true
        actionNotEmpty.effects.isEmpty() shouldNotBe true
    }
}