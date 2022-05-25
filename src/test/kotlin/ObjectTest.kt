import io.kotest.matchers.shouldBe
import kotlin.test.Test
import resources.TestUtils.name
import resources.TestUtils.objEmpty
import resources.TestUtils.objNotEmpty

class ObjectTest {
    @Test
    fun testEmptyCreation() {
        objEmpty.representation.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmptyCreation() {
        objNotEmpty.representation.replace("_[0-9]".toRegex(), "") shouldBe name
    }
}