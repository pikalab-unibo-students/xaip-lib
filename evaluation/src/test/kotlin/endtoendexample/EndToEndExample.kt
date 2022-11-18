package endtoendexample
import core.Plan
import core.Planner
import domain.BlockWorldDomain.Operators
import domain.BlockWorldDomain.Problems
import explanation.Explainer
import explanation.impl.BaseExplanationPresenter
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionPlanSatisfiability
import io.kotest.core.spec.style.AnnotationSpec

class EndToEndExample : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())
    @Test
    fun testPickC() {
        val initialPlan = Plan.of(
            listOf(
                Operators.unstackAB,
                Operators.putdownA,
                Operators.unstackCD,
                Operators.stackCA,
                Operators.pickD,
                Operators.stackDC,
                Operators.pickB,
                Operators.stackBD
            )
        )
        val question = QuestionAddOperator(
            Problems.unstackABunstackCDstackBDCA,
            initialPlan,
            Operators.putdownC,
            3
        )
        println(BaseExplanationPresenter(explainer.explain(question)).present())
        println(BaseExplanationPresenter(explainer.explain(question)).presentMinimalExplanation())
        println(BaseExplanationPresenter(explainer.explain(question)).presentContrastiveExplanation())
    }

    @Test
    fun idempotentActions() {
        val initialPlan = Plan.of(
            listOf(
                Operators.unstackAB,
                Operators.putdownA,
                Operators.unstackCD,
                Operators.putdownC,
                Operators.pickC,
                Operators.putdownC,
                Operators.stackCA,
                Operators.pickD,
                Operators.stackDC,
                Operators.pickB,
                Operators.stackBD
            )
        )
        val question = QuestionPlanSatisfiability(
            Problems.unstackABunstackCDstackBDCA,
            initialPlan
        )
        println(BaseExplanationPresenter(explainer.explain(question)).present())
        println(BaseExplanationPresenter(explainer.explain(question)).presentMinimalExplanation())
        println(BaseExplanationPresenter(explainer.explain(question)).presentContrastiveExplanation())
    }
}
