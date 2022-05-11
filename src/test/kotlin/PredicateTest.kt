import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import org.junit.Test
import resources.res.nameGC
import resources.res.predicateEmpty
import resources.res.predicateNotEmpty

class PredicateTest {

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