package dsl

import core.FluentBasedGoal
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.ProblemsDSL.problemOnAB
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class GoalDSLTest : AnnotationSpec() {
    @Test
    fun goalDSLworksAsExpected() {
        problemOnAB.goal shouldBe FluentBasedGoal.of(Fluents.onAB)
    }
}
