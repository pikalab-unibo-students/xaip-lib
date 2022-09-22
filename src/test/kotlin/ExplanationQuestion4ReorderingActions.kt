
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils.ContrastiveExplanation
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewGroundFluent
import resources.ExplanationUtils.createNewPredicate
import resources.ExplanationUtils.findAction
import resources.ExplanationUtils.reorderPlan
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.stackBA
import resources.domain.BlockWorldDomain.Operators.stackDC
import resources.domain.BlockWorldDomain.Planners
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class ExplanationQuestion4ReorderingActions : AnnotationSpec() {
    // 4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions

    data class Question4(
        val actionsToAnticipate: List<Operator>,
        val actionsToPosticipate: List<Operator>,
        val problem: Problem,
        val originalPlan: Plan
    )

    private val question = Question4(
        listOf(pickD, stackDC),
        listOf(pickB, stackBA),
        Problems.stackBAstackDC,
        Plan.of(listOf(pickB, stackBA, pickD, stackDC))
    )

    @Test
    fun test() {
        val predicates = mutableListOf<Predicate>()
        val fluents = mutableListOf<Fluent>()
        val newActions = mutableSetOf<Action>()
        // creare il DAG
        for (action in question.originalPlan.actions) {
            val predicate1 = createNewPredicate(action, "ordered_")
            val predicate2 = createNewPredicate(action, "traversed_")
            predicates.add(predicate1)
            predicates.add(predicate2)
        }

        val reorderedPlan = reorderPlan(
            question.originalPlan,
            question.actionsToPosticipate,
            question.actionsToAnticipate
        )

        for (action in reorderedPlan) {
            fluents.add(createNewGroundFluent(action, createNewPredicate(action, "traversed_")))
        }

        for (action in reorderedPlan) {
            if (!newActions.map {
                it.name.filter { char ->
                    char.isLetter()
                }
            }.equals(action.name)
            ) {
                newActions.add(
                    createNewAction(
                        findAction(action, question.problem.domain.actions),
                        createNewFluent(
                            action,
                            createNewPredicate(action, "traversed_")
                        )
                    )
                )
            }
        }
        println(newActions)
        val hDomain = Domain.of(
            question.problem.domain.name,
            predicates.also { it.addAll(question.problem.domain.predicates) }.toSet(),
            newActions,
            question.problem.domain.types
        )
        val hProblem = Problem.of(
            hDomain,
            question.problem.objects,
            question.problem.initialState,
            FluentBasedGoal.of(
                (question.problem.goal as FluentBasedGoal)
                    .targets.toMutableSet().also { it.addAll(fluents.reversed()) }
            )
        )
        val hPlan = Planners.stripsPlanner.plan(hProblem).first()

        val explanation = ContrastiveExplanation.of(question.originalPlan, hPlan, question.actionsToAnticipate.first())
        val prova = Operator.of(newActions.first()).apply(VariableAssignment.of(Values.X, Values.d))
        println(prova)
        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            Plan.of(listOf(pickD, stackDC, pickB, stackBA)),
            question.actionsToAnticipate.first()
        )
        explanation shouldBe contrastiveExplanation
    }
}
