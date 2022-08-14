package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.States
import resources.TestUtils.problemDSL

class StatesDSLTest : AnnotationSpec() {
    @Test
    fun statesDSLworksAsExpected() {
        problemDSL.initialState shouldBe States.initial
    }
}
