package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import domain.BlockWorldDomain.ProblemsDSL.problemOnAB
import domain.BlockWorldDomain.States

class StatesDSLTest : AnnotationSpec() {
    @Test
    fun statesDSLworksAsExpected() {
        problemOnAB.initialState shouldBe States.initial
    }
}
