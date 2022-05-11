import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.res.domainEmpty
import resources.res.domainNotEmpty
import resources.res.nameGC

class DomainTest {
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