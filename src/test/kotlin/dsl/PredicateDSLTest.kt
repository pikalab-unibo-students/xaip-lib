package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Predicates
import resources.TestUtils.domainDSL

class PredicateDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun predicatesDSLworksAsExpected() {
        domainDSL.predicates.size shouldNotBe 0
        Predicates.at shouldBeIn domainDSL.predicates
    }
}
