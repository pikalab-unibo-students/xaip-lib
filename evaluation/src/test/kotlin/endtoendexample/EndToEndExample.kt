package endtoendexample
import core.Plan
import core.Planner
import domain.BlockWorldDomain.Operators
import domain.BlockWorldDomain.Problems
import domain.LogisticDomain.Operators.loadC1fromL2onR
import domain.LogisticDomain.Operators.loadC2fromL3onR
import domain.LogisticDomain.Operators.moveRfromL1toL2
import domain.LogisticDomain.Operators.moveRfromL1toL3
import domain.LogisticDomain.Operators.moveRfromL2toL4
import domain.LogisticDomain.Operators.moveRfromL3toL1
import domain.LogisticDomain.Operators.moveRfromL4toL5
import domain.LogisticDomain.Operators.moveRfromL4toL6
import domain.LogisticDomain.Operators.unloadC1fromRtoL4
import domain.LogisticDomain.Operators.unloadC2fromRtoL1
import domain.LogisticDomain.States.alternativeState
import explanation.Explainer
import explanation.impl.BaseExplanationPresenter
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionPlanSatisfiability
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec
import domain.LogisticDomain.Problems as LogisticProblem

class EndToEndExample : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())

    @Test
    fun addOperatorBlockWorld() {
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
    fun planSatisfiabilityBlockWorld() {
        val initialPlan = Plan.of(
            listOf(
                Operators.unstackAB,
                Operators.putdownA,
                Operators.unstackCD,
                Operators.putdownC,
                Operators.pickC,
                Operators.stackCA,
                Operators.pickD,
                Operators.stackDC,
                Operators.pickB,
                Operators.stackBD
            )
        )
        val question = QuestionPlanSatisfiability(Problems.unstackABunstackCDstackBDCA, initialPlan)
        println(BaseExplanationPresenter(explainer.explain(question)).present())
        println(BaseExplanationPresenter(explainer.explain(question)).presentMinimalExplanation())
        println(BaseExplanationPresenter(explainer.explain(question)).presentContrastiveExplanation())
    }

    @Test
    fun replaceActionInStateLogisticDomain() {
        val plan = Plan.of(
            listOf(
                moveRfromL1toL3,
                loadC2fromL3onR,
                moveRfromL3toL1,
                unloadC2fromRtoL1,
                moveRfromL1toL2,
                loadC1fromL2onR,
                moveRfromL2toL4,
                unloadC1fromRtoL4,
                moveRfromL4toL5
            )
        )

        val question = QuestionReplaceOperator(
            LogisticProblem.robotFromLoc1ToLoc5Container1FromLoc2ToLoc4Container2FromLoc3ToLoc1AlternativeInitialState,
            plan,
            moveRfromL4toL6,
            8,
            alternativeState
        )
        println(BaseExplanationPresenter(explainer.explain(question)).present())
        // println(BaseExplanationPresenter(explainer.explain(question)).presentMinimalExplanation())
        // println(BaseExplanationPresenter(explainer.explain(question)).presentContrastiveExplanation())
    }
}
