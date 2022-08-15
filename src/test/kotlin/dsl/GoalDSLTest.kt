package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Fluents
import resources.TestUtils.problemDSL

class GoalDSLTest : AnnotationSpec() {
    @Test
    fun goalDSLworksAsExpected() {
        problemDSL.goal shouldBe FluentBasedGoal.of(Fluents.onAB)
    }
}
