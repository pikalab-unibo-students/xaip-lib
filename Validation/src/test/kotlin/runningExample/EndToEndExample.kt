package runningExample
import core.Plan
import core.Planner
import domain.BlockWorldDomain
import explanation.Explainer
import explanation.impl.ContrastiveExplanationPresenter
import explanation.impl.QuestionAddOperator
import io.kotest.core.spec.style.AnnotationSpec

class EndToEndExample : AnnotationSpec() {
    @Test
    fun testPickC() {
        val initialPlan = Plan.of(
            listOf(
                BlockWorldDomain.Operators.unstackAB,
                BlockWorldDomain.Operators.putdownA,
                BlockWorldDomain.Operators.unstackCD,
                BlockWorldDomain.Operators.stackCA,
                BlockWorldDomain.Operators.pickD,
                BlockWorldDomain.Operators.stackDC,
                BlockWorldDomain.Operators.pickB,
                BlockWorldDomain.Operators.stackBD
            )
        )
        val question = QuestionAddOperator(
            BlockWorldDomain.Problems.unstackABunstackCDstackBDCA,
            initialPlan,
            BlockWorldDomain.Operators.putdownC,
            3
        )
        println(ContrastiveExplanationPresenter(Explainer.of(Planner.strips()).explain(question)).present())
    }
}
