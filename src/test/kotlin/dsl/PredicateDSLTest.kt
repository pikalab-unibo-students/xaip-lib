package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Predicates


import resources.TestUtils.domainDSL

class PredicateDSLTest : AnnotationSpec() {
    @Test
    fun predicatesDSLworksAsExpected() {
        domainDSL.predicates.size shouldBe 4
        Predicates.armEmpty shouldBe domainDSL.predicates.first()
    }
}
