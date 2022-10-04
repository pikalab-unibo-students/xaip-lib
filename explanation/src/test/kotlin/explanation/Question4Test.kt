package explanation

import explanation.impl.Question4
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain

class Question4Test : AnnotationSpec() {
    /**
     * Why not this plan instead.
     */

    @Test
    fun `Test scemo`() {
        val planPickDStackDCpickA = Plan.of(listOf(BlockWorldDomain.Operators.pickD, BlockWorldDomain.Operators.stackDC, BlockWorldDomain.Operators.pickA))
        val planPickB = Plan.of(listOf(BlockWorldDomain.Operators.pickB))
        val q4 = Question4(BlockWorldDomain.Problems.armNotEmpty, planPickB, planPickDStackDCpickA)
        if(q4.isValid()) {
            val explanation = Explanation.of(q4.plan, q4.alternativePlan)

            val contrastiveExplanation = Explanation.of(
                q4.plan,
                q4.alternativePlan
            )
            explanation shouldBe contrastiveExplanation
        }
    }
}
