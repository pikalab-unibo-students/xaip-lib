import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test
import resources.res.nameGC

class DomainTest {
    private val domainEmpty = mockkClass(Domain::class){
        every{ name } returns ""
        every { predicates } returns emptySet()
        every { actions } returns emptySet()
        every { types } returns emptySet()
        every { axioms } returns emptySet()
    }
    private val domainNotEmpty = mockkClass(Domain::class){
        every{ name } returns nameGC
        every { predicates } returns mockk(relaxed = true)
        every { actions } returns mockk(relaxed = true)
        every { types } returns mockk(relaxed = true)
        every { axioms } returns mockk(relaxed = true)
    }

    @Test
    fun testEmptyCreation() {
        domainEmpty.name shouldBe ""
        domainEmpty.predicates.isEmpty() shouldBe true
        domainEmpty.actions.isEmpty() shouldBe true
        domainEmpty.types.isEmpty() shouldBe true
        domainEmpty.axioms.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmptyCreation() {
        domainNotEmpty.name shouldBe nameGC
        domainNotEmpty.predicates.isEmpty() shouldBe false
        domainNotEmpty.actions.isEmpty() shouldBe false
        domainNotEmpty.types.isEmpty() shouldBe false
        domainNotEmpty.axioms.isEmpty() shouldBe false
    }
}