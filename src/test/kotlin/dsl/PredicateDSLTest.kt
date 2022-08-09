package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Predicates
import resources.TestUtils.domainDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class PredicateDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun predicatesDSLworksAsExpected() {
        domainDSL.predicates.size shouldNotBe 0
        Predicates.at shouldBeIn domainDSL.predicates
    }
}
