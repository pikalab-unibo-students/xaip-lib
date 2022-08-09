package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import resources.TestUtils.Fluents
import resources.TestUtils.domainDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class AbstractFluentDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun abstractFluentDSLworksAsExpected() {
        val fluents = domainDSL.predicates.first().arguments
        Fluents.atAFloor shouldBeIn fluents
    }
}
