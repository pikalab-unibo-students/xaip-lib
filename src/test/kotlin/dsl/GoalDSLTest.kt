package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import resources.TestUtils.problemDSL
import resources.TestUtils.Goals

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class GoalDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun goalDSLworksAsExpected() {
        val goals = problemDSL.goal
        Goals.pickX.shouldBeIn(goals)
    }
}
