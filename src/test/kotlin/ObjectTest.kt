import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.res.nameGC
import resources.res.objEmpty
import resources.res.objNotEmpty

class ObjectTest {
    @Test
    fun testEmptyCreation() {
        objEmpty.representation shouldBe ""
    }

    @Test
    fun testNotEmptyCreation() {
        objNotEmpty.representation shouldBe nameGC
    }
}