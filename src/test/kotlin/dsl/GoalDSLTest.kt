package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import resources.TestUtils.problemDSL
import resources.TestUtils.Goals

class GoalDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun goalDSLworksAsExpected() {
        val goals = problemDSL.goal
        Goals.pickX.shouldBeIn(goals)
    }
}
