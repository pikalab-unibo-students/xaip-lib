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
import explanation.ContrastiveExplanationPresenter
import explanation.Explainer
import explanation.impl.QuestionAddOperator
import explanation.impl.QuestionPlanProposal
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec
import domain.LogisticDomain.Problems as LogisticProblem

class EndToEndExample : AnnotationSpec() {
    private val explainer = Explainer.of(Planner.strips())

    private val formerPlan = Plan.of(
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

    @Test
    fun addOperatorBlockWorld() {
        val question = QuestionAddOperator(
            Problems.unstackABunstackCDstackBDCA,
            formerPlan,
            Operators.putdownC,
            3
        )
        val explanation = explainer.explain(question)
        println(ContrastiveExplanationPresenter.of(explanation).present())
        println(ContrastiveExplanationPresenter.of(explanation).presentMinimalExplanation())
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
    }

    @Test
    fun planProposalBlockWorld() {
        val planProposal = Plan.of(
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
        val question = QuestionPlanProposal(Problems.unstackABunstackCDstackBDCA, formerPlan, planProposal)
        val explanation = explainer.explain(question)

        println(ContrastiveExplanationPresenter.of(explanation).present())
        println(ContrastiveExplanationPresenter.of(explanation).presentMinimalExplanation())
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
    }

    @Test
    fun replaceActionInStateLogisticDomain() {
        val formerPlan = Plan.of(
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
            formerPlan,
            moveRfromL4toL6,
            8,
            alternativeState
        )
        val explanation = explainer.explain(question)
        println(ContrastiveExplanationPresenter.of(explanation).present())
        println(ContrastiveExplanationPresenter.of(explanation).presentMinimalExplanation())
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
    }
}
