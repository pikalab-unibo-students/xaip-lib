package dsl

import dsl.provider.PredicateProvider
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Predicates

class PredicatesProviderTest : AnnotationSpec() {
    private val predicateProvider = PredicateProvider.of(setOf(Predicates.on, Predicates.armEmpty))

    @Test
    fun testPredicateExists() {
        predicateProvider.findPredicate(Predicates.on.name, Predicates.on.arguments.size) shouldNotBe null
        predicateProvider.findPredicate(Predicates.armEmpty.name, Predicates.armEmpty.arguments.size) shouldNotBe null
    }

    @Test
    fun testPredicateNotExists() {
        predicateProvider.findPredicate(Predicates.on.name, Predicates.on.arguments.size - 1) shouldBe null
        predicateProvider.findPredicate(Predicates.armEmpty.name, Predicates.armEmpty.arguments.size + 1) shouldBe null
        predicateProvider.findPredicate("nothing", 1) shouldBe null
    }
}
