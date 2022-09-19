import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.buildHproblem
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewGroundFluent
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Operators.stackAB
import resources.domain.BlockWorldDomain.Operators.stackBA
import resources.domain.BlockWorldDomain.Operators.stackCA
import resources.domain.BlockWorldDomain.Operators.stackDC

class ExplanationQuestion4ReorderingActions : AnnotationSpec() {
    // 4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions
    data class Question4(
        val action1: Operator,
        val action2: Operator,
        val problem: Problem,
        val originalPlan: Plan
    )

    val question = Question4(
        pickC,
        pickA,
        BlockWorldDomain.Problems.stackZWpickX,
        Plan.of(listOf(pickA, stackAB, pickC))
    )

    fun newPredicate1(action: Action, string: String): Predicate =
        Predicate.of(string + action.name, action.parameters.values.toList())

    fun createNewAction1(action: Action, fluent1: Set<Fluent>, fluent2: Fluent): Action {
        return Action.of(
            name = action.name + "^",
            parameters = action.parameters,
            preconditions = fluent1.toMutableSet().also {
                it.addAll(action.preconditions)
            },
            effects = mutableSetOf(Effect.of(fluent2)).also {
                it.addAll(action.effects)
            }
        )
    }

    @Test
    fun test() {
        println("action list, original order ${question.originalPlan.actions}")

        val predicates = mutableListOf<Predicate>()
        val fluents = mutableListOf<Fluent>()

        //creare il DAG

        for (action in question.originalPlan.actions) {
            val predicate1 = newPredicate1(action, "ordered_")
            val predicate2 = newPredicate1(action, "traversed_")
            predicates.add(predicate1)
            predicates.add(predicate2)
        }

        val hPlan: Plan = TODO()
        val explanation: ExplanationUtils.ContrastiveExplanation =
            ExplanationUtils.buildExplanation(question.originalPlan, hPlan, question.action1)

        val contrastiveExplanation = ExplanationUtils.ContrastiveExplanation.of(
            question.originalPlan,
            Plan.of(listOf(pickC, stackCA, pickB)),
            question.action1
        )
        explanation shouldBe contrastiveExplanation
    }
}
