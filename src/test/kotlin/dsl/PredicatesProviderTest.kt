package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.domainDSL

class PredicatesProviderTest : AnnotationSpec() {

    @Test
    fun testArmEmptyPredicateExists() {
        TestUtils.Predicates.armEmpty shouldBeIn domainDSL.predicates
        TestUtils.Predicates.armEmpty.name shouldBe domainDSL.predicates.last().name
        TestUtils.Predicates.armEmpty.arguments shouldBe domainDSL.predicates.last().arguments
        TestUtils.Predicates.armEmpty.arguments.first().superType shouldBe
            domainDSL.predicates.last().arguments.first().superType
    }

    @Test
    fun testOnPredicateExists() {
        TestUtils.Predicates.on shouldBeIn domainDSL.predicates
        TestUtils.Predicates.on.name shouldBe domainDSL.predicates.first().name
        TestUtils.Predicates.on.arguments shouldBe domainDSL.predicates.first().arguments
        TestUtils.Predicates.on.arguments[1].name shouldBe domainDSL.predicates.first().arguments[1].name
        TestUtils.Predicates.on.arguments[1].superType shouldBe domainDSL.predicates.first().arguments[1].superType
        TestUtils.Predicates.on.arguments[2].name shouldBe domainDSL.predicates.first().arguments[2].name
        TestUtils.Predicates.on.arguments[2].superType shouldBe domainDSL.predicates.first().arguments[2].superType
    }

    @Test
    fun testPredicateNotExists() {
        Predicate.of("nothing") shouldNotBeIn domainDSL.predicates
    }
}
