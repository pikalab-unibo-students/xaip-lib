package dsl

import domain.BlockWorldDomain.ProblemsDSL.problemOnAB
import domain.BlockWorldDomain.States
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class StatesDSLTest : AnnotationSpec() {
    @Test
    fun statesDSLworksAsExpected() {
        problemOnAB.initialState shouldBe States.initial
    }
}
