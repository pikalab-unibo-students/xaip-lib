import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test
import resources.res.nameGC

class PredicateTest {
    private val predicateEmpty = mockkClass(Predicate::class){
        every{ name } returns ""
        every { arguments } returns emptyList()
    }
    private val predicateNotEmpty = mockkClass(Predicate::class){
        every{ name } returns nameGC
        every { arguments } returns mockk(relaxed = true)
    }
    @Test
    fun testPredicateEmptyCreation() {
        predicateEmpty.name shouldBe ""
        predicateEmpty.arguments.isEmpty() shouldBe true
    }
    @Test
    fun testPredicateNotEmptyCreation() {
        predicateNotEmpty.name shouldBe nameGC
        predicateNotEmpty.arguments.isEmpty() shouldNotBe true
    }
}