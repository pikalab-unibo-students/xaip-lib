import io.kotest.matchers.shouldBe
import resources.TestUtils.name
import resources.TestUtils.objEmpty
import resources.TestUtils.objNotEmpty
import resources.TestUtils.substitution
import kotlin.test.Test

class ObjectTest {
    @Test
    fun testEmptyCreation() {
        objEmpty.representation.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmptyCreation() {
        objNotEmpty.representation.replace("_[0-9]".toRegex(), "") shouldBe name
    }

    @Test
    fun testCommonProperties() {
        objEmpty.apply(substitution) shouldBe objEmpty
        objEmpty.isGround shouldBe true

        objNotEmpty.apply(substitution) shouldBe objNotEmpty
        objNotEmpty.isGround shouldBe true
    }
}