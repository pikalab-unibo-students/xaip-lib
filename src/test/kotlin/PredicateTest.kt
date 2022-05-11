import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test
import resources.res.nameGC

class PredicateTest {
    private val type1= TypeImpl()
    private val predicateEmpty = PredicateImpl("", emptyList())
    private val predicateNotEmpty = PredicateImpl(nameGC, listOf(type1))

    @Test
    fun testEmptyCreation() {
        predicateEmpty.name shouldBe ""
        predicateEmpty.arguments.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmptyCreation() {
        predicateNotEmpty.name shouldBe nameGC
        predicateNotEmpty.arguments.isEmpty() shouldNotBe true
    }
}