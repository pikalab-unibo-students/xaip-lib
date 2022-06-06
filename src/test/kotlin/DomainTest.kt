import io.kotest.matchers.shouldBe
import resources.TestUtils.domainEmpty
import resources.TestUtils.domainNotEmpty
import resources.TestUtils.name
import kotlin.test.Test

class DomainTest {
    @Test
    fun testEmptyCreation() {
        domainEmpty.name.isEmpty() shouldBe true
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