package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.ProblemsDSL.problemOnAB
import resources.TestUtils.States

class StatesDSLTest : AnnotationSpec() {
    @Test
    fun statesDSLworksAsExpected() {
        problemOnAB.initialState shouldBe States.initial
    }
}
