import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import org.junit.Test
import resources.Res.name
import resources.Res.predicateEmpty
import resources.Res.predicateNotEmpty
import resources.Res.size
import resources.Res.type1

class PredicateTest {
    @Test
    fun testEmptyCreation() {
        predicateEmpty.name shouldBe ""
        predicateEmpty.arguments.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmptyCreation() {
        predicateNotEmpty.name shouldBe name
        predicateNotEmpty.arguments.isEmpty() shouldNotBe true
        predicateNotEmpty.arguments.size shouldBe size
        predicateNotEmpty.arguments.forEach {it shouldBe type1}
    }
}