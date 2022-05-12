import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.Res.domainEmpty
import resources.Res.domainNotEmpty
import resources.Res.name

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
        domainNotEmpty.name shouldBe name
        domainNotEmpty.predicates.isEmpty() shouldBe false
        domainNotEmpty.actions.isEmpty() shouldBe false
        domainNotEmpty.types.isEmpty() shouldBe false
        domainNotEmpty.axioms.isEmpty() shouldBe false
    }
}