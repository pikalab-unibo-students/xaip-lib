package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import resources.TestUtils
import resources.TestUtils.domainDSL

class EffectsDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun effectsDSLworksAsExpected() {
        val effects = domainDSL.actions.first().effects
        TestUtils.Effects.atXFloor shouldBeIn effects
    }
}
