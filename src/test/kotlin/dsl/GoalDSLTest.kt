package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.BlockWorldDomain.Fluents
import resources.BlockWorldDomain.ProblemsDSL.problemOnAB

class GoalDSLTest : AnnotationSpec() {
    @Test
    fun goalDSLworksAsExpected() {
        problemOnAB.goal shouldBe FluentBasedGoal.of(Fluents.onAB)
    }
}
