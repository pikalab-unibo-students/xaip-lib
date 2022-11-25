package endtoendexample
import core.Plan
import core.Planner
import domain.BlockWorldDomain.Operators
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
import dsl.domain
import dsl.problem
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

    private val domainDSL = domain {
        name = "block_world"
        types {
            +"anything"
            +"strings"("anything")
            +"blocks"("strings")
            +"locations"("strings")
        }
        predicates {
            +"at"("blocks", "locations")
            +"on"("blocks", "blocks")
            +"arm_empty"
            +"clear"("blocks")
        }
        actions {
            "pick" {
                parameters {
                    "X" ofType "blocks"
                }
                preconditions {
                    +"arm_empty"()
                    +"clear"("X")
                    +"at"("X", "floor")
                }
                effects {
                    +"at"("X", "arm")
                    -"arm_empty"
                    -"at"("X", "floor")
                    -"clear"("X")
                }
            }
            "stack" {
                parameters {
                    "X" ofType "blocks"
                    "Y" ofType "locations"
                }
                preconditions {
                    +"at"("X", "arm")
                    +"clear"("Y")
                }
                effects {
                    +"on"("X", "Y")
                    +"clear"("X")
                    +"arm_empty"
                    -"at"("X", "arm")
                    -"clear"("Y")
                }
            }

            "unstack" {
                parameters {
                    "X" ofType "blocks"
                    "Y" ofType "locations"
                }
                preconditions {
                    +"on"("X", "Y")
                    +"clear"("X")
                    +"arm_empty"
                }
                effects {
                    -"on"("X", "Y")
                    -"clear"("X")
                    -"arm_empty"
                    +"at"("X", "arm")
                    +"clear"("Y")
                }
            }
            "putdown" {
                parameters {
                    "X" ofType "blocks"
                }
                preconditions {
                    +"at"("X", "arm")
                    +"clear"("Y")
                }
                effects {
                    -"at"("X", "arm")
                    +"clear"("X")
                    +"arm_empty"
                    +"at"("X", "floor")
                }
            }
        }
    }

    private val problemDSL = problem(domainDSL) {
        objects {
            +"blocks"("a", "b", "c", "d")
            +"locations"("floor", "arm")
        }
        initialState {
            +"on"("a", "b")
            +"on"("c", "d")
            +"arm_empty"
            +"clear"("a")
            +"clear"("c")
            +"at"("b", "floor")
            +"at"("d", "floor")
        }
        goals {
            +"clear"("b")
            +"on"("b", "d")
            +"on"("d", "c")
            +"on"("c", "a")
            +"at"("a", "floor")
        }
    }

    @Test
    fun addOperatorBlockWorld() {
        val question = QuestionAddOperator(
            problemDSL,
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
        val question = QuestionPlanProposal(problemDSL, formerPlan, planProposal)
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
