package explanation

import explanation.impl.Question4
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import domain.BlockWorldDomain.Operators
import domain.BlockWorldDomain.Problems

class Question4Test : AnnotationSpec() {
    val problem = Problems.armNotEmpty
    val planPickBStackBA = Plan.of(listOf(Operators.pickB, Operators.stackBA))
    val planPickB = Plan.of(listOf(Operators.pickB))
    val planPickBstackBApickC = Plan.of(listOf(Operators.pickB, Operators.stackBA, Operators.pickC))

    @Test
    fun `Test valid plan`() {
        val q4 = Question4(problem, planPickBstackBApickC, planPickB, planPickB.actions.first(), 0)
        val explanation = Explanation.of(q4.plan, q4.alternativePlan, q4)
        explanation.isPlanValid() shouldBe true
    }

    @Test
    fun `Test incorrect plan`() {
        val q4 = Question4(problem, planPickBStackBA, planPickB, planPickB.actions.first(), 0)
        val explanation = Explanation.of(q4.plan, q4.alternativePlan, q4)
        explanation.isPlanValid() shouldBe false
    }
}
