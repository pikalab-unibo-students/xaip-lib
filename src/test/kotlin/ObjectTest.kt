import impl.ObjectImpl
import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.res.nameGC

class ObjectTest {
    private val objEmpty= ObjectImpl("")
    private val objNotEmpty=ObjectImpl(nameGC)

    @Test
    fun testEmptyCreation() {
        objEmpty.representation shouldBe ""
    }

    @Test
    fun testNotEmptyCreation() {
        objNotEmpty.representation shouldBe nameGC
    }
}