package endtoendexample

import core.Plan
import core.Planner
import domain.LogisticDomain
import dsl.domain
import explanation.ContrastiveExplanationPresenter
import explanation.Explainer
import explanation.impl.QuestionReplaceOperator
import io.kotest.core.spec.style.AnnotationSpec.Test

class endToEndExampleLogistic {
    private val explainer = Explainer.of(Planner.strips())

    val domain = domain {
        name = "logistic_world"
        types {
            +"anything"
            +"string"("anything")
            +"locations"("string")
            +"robots"("string")
            +"containers"("string")
        }
        predicates {
            +"connected"("locations", "locations")
            +"atLocation"("robots", "locations")
            +"loaded"("robots", "containers")
            +"unloaded"("robots")
            +"inContainerLocation"("containers", "robots")
        }
        actions {
            "move" {
                parameters {
                    "X" ofType "robots"
                    "Y" ofType "locations"
                    "Z" ofType "locations"
                }
                preconditions {
                    +"connected"("Y", "Z")
                    +"atLocation"("X", "Y")
                }
                effects {
                    +"atLocation"("X", "Z")
                    -"atLocation"("X", "Y")
                }
            }
            "load" {
                parameters {
                    "X" ofType "robots"
                    "Y" ofType "containers"
                    "Z" ofType "locations"
                }
                preconditions {
                    +"atLocation"("X", "Z")
                    +"inContainerLocation"("Y", "Z")
                }
                effects {
                    +"loaded"("X", "Y")
                    -"inContainerLocation"("Y", "Z")
                }
            }
            "unload" {
                parameters {
                    "X" ofType "robots"
                    "Z" ofType "locations"
                    "Y" ofType "containers"
                }
                preconditions {
                    +"atLocation"("X", "Z")
                    +"loaded"("X", "Y")
                }
                effects {
                    +"inContainerLocation"("Y", "Z")
                    -"loaded"("X", "y")
                }
            }
        }
    }
    @Test
    fun replaceActionInStateLogisticDomain() {
        val formerPlan = Plan.of(
            listOf(
                LogisticDomain.Operators.moveRfromL1toL3,
                LogisticDomain.Operators.loadC2fromL3onR,
                LogisticDomain.Operators.moveRfromL3toL1,
                LogisticDomain.Operators.unloadC2fromRtoL1,
                LogisticDomain.Operators.moveRfromL1toL2,
                LogisticDomain.Operators.loadC1fromL2onR,
                LogisticDomain.Operators.moveRfromL2toL4,
                LogisticDomain.Operators.unloadC1fromRtoL4,
                LogisticDomain.Operators.moveRfromL4toL5
            )
        )

        val question = QuestionReplaceOperator(
            LogisticDomain.Problems.robotFromLoc1ToLoc5Container1FromLoc2ToLoc4Container2FromLoc3ToLoc1AlternativeInitialState,
            formerPlan,
            LogisticDomain.Operators.moveRfromL4toL6,
            8,
            LogisticDomain.States.alternativeState
        )
        val explanation = explainer.explain(question)
        println(ContrastiveExplanationPresenter.of(explanation).present())
        println(ContrastiveExplanationPresenter.of(explanation).presentMinimalExplanation())
        println(ContrastiveExplanationPresenter.of(explanation).presentContrastiveExplanation())
    }
}