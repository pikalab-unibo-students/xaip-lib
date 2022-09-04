package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import io.kotest.matchers.shouldBe
import resources.BlockWorldDomain.Domains.blockWorld
import resources.BlockWorldDomain.Predicates

class PredicateDSLTest : AnnotationSpec() {
    @Test
    fun testClearPredicateExists() {
        Predicates.clear shouldBeIn blockWorld.predicates
        Predicates.clear.name shouldBe blockWorld.predicates.last().name
        Predicates.clear.arguments shouldBe blockWorld.predicates.last().arguments
    }

    @Test
    fun testAtPredicateExists() {
        Predicates.at shouldBeIn blockWorld.predicates
        Predicates.at.name shouldBe blockWorld.predicates.first().name
        Predicates.at.arguments shouldBe blockWorld.predicates.first().arguments
        Predicates.at.arguments.first().name shouldBe blockWorld.predicates.first().arguments.first().name
        Predicates.at.arguments.first().superType shouldBe
            blockWorld.predicates.first().arguments.first().superType
        Predicates.at.arguments.last().name shouldBe
            blockWorld.predicates.first().arguments.last().name
        Predicates.at.arguments.last().superType shouldBe
            blockWorld.predicates.first().arguments.last().superType
    }

    @Test
    fun testPredicateNotExists() {
        Predicate.of("nothing") shouldNotBeIn blockWorld.predicates
    }
}
