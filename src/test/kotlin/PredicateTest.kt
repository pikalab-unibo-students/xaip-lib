import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Predicates
import resources.TestUtils.Types
import resources.TestUtils.name
import resources.TestUtils.predicateEmpty
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.predicates
import resources.TestUtils.size
import resources.TestUtils.type1
import resources.TestUtils.types

class PredicateTest : AnnotationSpec() {

    @Test
    fun testPredicateConstructor() {
        val name = "under"
        val predicate1 = Predicate.of(name, listOf(Types.anything))
        val predicate2 = Predicate.of(name, Types.anything, Types.strings)
        predicate1.name shouldBe name
        predicate1.arguments.size shouldBe 1
        predicate1.arguments.first() shouldBe Types.anything
        predicate2.name shouldBe name
        predicate2.arguments.size shouldBe 2
        predicate2.arguments.first() shouldBe Types.anything
        predicate2.arguments.last() shouldBe Types.strings
    }

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
        predicateNotEmpty.arguments.forEach { it shouldBe type1 }
    }

    @Test
    fun testPredicateObjectWorksAsExpected() {
        Predicates.at shouldBeIn predicates
        Predicates.at.arguments.isEmpty() shouldNotBe true
        Predicates.at.arguments.forEach { it shouldBeIn types }
    }
}
