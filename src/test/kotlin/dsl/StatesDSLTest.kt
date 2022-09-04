package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.ProblemsDSL.problemOnAB
import resources.domain.BlockWorldDomain.States

class StatesDSLTest : AnnotationSpec() {
    @Test
    fun statesDSLworksAsExpected() {
        problemOnAB.initialState shouldBe States.initial
    }
}
