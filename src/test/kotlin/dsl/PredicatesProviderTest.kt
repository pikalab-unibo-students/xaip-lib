package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import resources.TestUtils
import resources.TestUtils.domainDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class PredicatesProviderTest : AnnotationSpec() {
    @Ignore
    @Test
    fun testPredicateExists() {
        TestUtils.Predicates.armEmpty shouldBeIn domainDSL.predicates
        TestUtils.Predicates.at shouldBeIn domainDSL.predicates
    }
    @Ignore
    @Test
    fun testPredicateNotExists() {
        TestUtils.Predicates.at shouldNotBeIn domainDSL.predicates
    }
}
