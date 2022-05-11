import impl.DomainImpl
import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.res.actionNotEmpty
import resources.res.axiomNotEmpty
import resources.res.nameGC
import resources.res.predicateNotEmpty
import resources.res.type1

class DomainTest {
    private val domainEmpty = DomainImpl("", emptySet(), emptySet(),emptySet(), emptySet())
    private val domainNotEmpty = DomainImpl(nameGC, setOf(predicateNotEmpty), setOf(actionNotEmpty),setOf(type1), setOf(axiomNotEmpty))

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