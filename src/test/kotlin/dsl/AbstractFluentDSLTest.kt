package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import resources.TestUtils.Fluents
import resources.TestUtils.domainDSL

class AbstractFluentDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun abstractFluentDSLworksAsExpected() {
        val fluents = domainDSL.predicates.first().arguments
        Fluents.atAFloor shouldBeIn fluents
    }
}
