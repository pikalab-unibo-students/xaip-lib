package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.ProblemsDSL.problemOnAB

class GoalDSLTest : AnnotationSpec() {
    @Test
    fun goalDSLworksAsExpected() {
        problemOnAB.goal shouldBe FluentBasedGoal.of(Fluents.onAB)
    }
}
