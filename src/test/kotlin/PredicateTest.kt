import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import org.junit.Test
import resources.TestUtils.name
import resources.TestUtils.predicateEmpty
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.type1

class PredicateTest {
    @Test
    fun testEmptyCreation() {
        predicateEmpty.name.isEmpty() shouldBe true
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