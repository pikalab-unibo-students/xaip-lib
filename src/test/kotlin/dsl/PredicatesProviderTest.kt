package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.domainDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class PredicatesProviderTest : AnnotationSpec() {

    @Test
    fun testArmEmptyPredicateExists() {
        TestUtils.Predicates.armEmpty shouldBeIn domainDSL.predicates
        TestUtils.Predicates.armEmpty.name shouldBe domainDSL.predicates.last().name
        TestUtils.Predicates.armEmpty.arguments shouldBe domainDSL.predicates.last().arguments
    }

    @Test
    fun testOnPredicateExists() {
        TestUtils.Predicates.on.name shouldBe domainDSL.predicates.first().name
        TestUtils.Predicates.on.arguments[1].name shouldBe domainDSL.predicates.first().arguments[1].name
        TestUtils.Predicates.on.arguments[1].superType shouldBe domainDSL.predicates.first().arguments[1].superType
        // TestUtils.Predicates.on shouldBeIn domainDSL.predicates
    }

    @Ignore
    @Test
    fun testPredicateNotExists() {
        TestUtils.Predicates.at shouldNotBeIn domainDSL.predicates
    }
}
