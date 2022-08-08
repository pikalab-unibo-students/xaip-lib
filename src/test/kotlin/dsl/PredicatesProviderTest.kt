package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import resources.TestUtils
import resources.TestUtils.domainDSL

class PredicatesProviderTest : AnnotationSpec() {
    @Ignore
    @Test
    fun testPredicateExists() {
        TestUtils.Predicates.on shouldBeIn domainDSL.predicates
    }

    @Ignore
    @Test
    fun testPredicateNotExists() {
       TestUtils.Predicates.at shouldNotBeIn domainDSL.predicates
    }
}
