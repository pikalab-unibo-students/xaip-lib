package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.Predicates
import resources.TestUtils.domainDSL

class PredicateDSLTest : AnnotationSpec() {
    @Test
    fun testArmEmptyPredicateExists() {
        Predicates.armEmpty shouldBeIn domainDSL.predicates
        Predicates.armEmpty.name shouldBe domainDSL.predicates.last().name
        Predicates.armEmpty.arguments shouldBe domainDSL.predicates.last().arguments
    }

    @Test
    fun testOnPredicateExists() {
        Predicates.on shouldBeIn domainDSL.predicates
        Predicates.on.name shouldBe domainDSL.predicates.first().name
        Predicates.on.arguments shouldBe domainDSL.predicates.first().arguments
        Predicates.on.arguments.first().name shouldBe domainDSL.predicates.first().arguments.first().name
        Predicates.on.arguments.first().superType shouldBe
            domainDSL.predicates.first().arguments.first().superType
        Predicates.on.arguments.last().name shouldBe
            domainDSL.predicates.first().arguments.last().name
        Predicates.on.arguments.last().superType shouldBe
            domainDSL.predicates.first().arguments.last().superType
    }

    @Test
    fun testPredicateNotExists() {
        Predicate.of("nothing") shouldNotBeIn domainDSL.predicates
    }
}
