package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.States
import resources.TestUtils.problemDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class StatesDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun statesDSLworksAsExpected() {
        problemDSL.initialState shouldBe States.initial
    }
}
