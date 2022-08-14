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
    }

    @Test
    fun testOnPredicateExists() {
        TestUtils.Predicates.on shouldBeIn domainDSL.predicates
        TestUtils.Predicates.on.name shouldBe domainDSL.predicates.first().name
        TestUtils.Predicates.on.arguments shouldBe domainDSL.predicates.first().arguments
        TestUtils.Predicates.on.arguments.first().name shouldBe domainDSL.predicates.first().arguments.first().name
        TestUtils.Predicates.on.arguments.first().superType shouldBe
                domainDSL.predicates.first().arguments.first().superType
        TestUtils.Predicates.on.arguments.last().name shouldBe
                domainDSL.predicates.first().arguments.last().name
        TestUtils.Predicates.on.arguments.last().superType shouldBe
                domainDSL.predicates.first().arguments.last().superType
    }

    @Test
    fun testPredicateNotExists() {
        Predicate.of("nothing") shouldNotBeIn domainDSL.predicates
    }
}
