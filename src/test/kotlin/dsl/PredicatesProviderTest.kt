package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import resources.TestUtils
import resources.TestUtils.domainDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class PredicatesProviderTest : AnnotationSpec() {
    @Test
    fun testPredicateExists() {
        TestUtils.Predicates.armEmpty shouldBeIn domainDSL.predicates
        // TestUtils.Predicates.clear shouldBeIn domainDSL.predicates
    }

    @Test
    fun testPredicateNotExists() {
        Predicate.of("nothing", emptyList()) shouldNotBeIn domainDSL.predicates
    }
}
