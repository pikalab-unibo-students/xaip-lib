import io.kotest.matchers.shouldBe
import org.junit.Test
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
        objNotEmpty.representation shouldBe name
    }
}