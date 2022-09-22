
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.ContrastiveExplanation
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewGroundFluent
import resources.ExplanationUtils.createNewPredicate
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.stackBA
import resources.domain.BlockWorldDomain.Operators.stackDC

class ExplanationQuestion4ReorderingActions : AnnotationSpec() {
    // 4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions
    data class Question4(
        val actionsToAnticipate: List<Operator>,
        val actionsToPosticipate: List<Operator>,
        val problem: Problem,
        val originalPlan: Plan
    )

    val question = Question4(
        listOf(pickD, stackDC),
        listOf(pickB, stackBA),
        BlockWorldDomain.Problems.stackBAstackDC,
        Plan.of(listOf(pickB, stackBA, pickD, stackDC))
    )

    @Test
    fun test() {
        println("action list, original order ${question.originalPlan.actions}")

        val predicates = mutableListOf<Predicate>()
        val fluents = mutableListOf<Fluent>()

        // creare il DAG
        for (action in question.originalPlan.actions) {
            val predicate1 = createNewPredicate(action, "ordered_")
            val predicate2 = createNewPredicate(action, "traversed_")
            predicates.add(predicate1)
            predicates.add(predicate2)
        }
        val indiceInizio = question.originalPlan.actions.indexOf(question.actionsToPosticipate.first())
        val indiceFine = question.originalPlan.actions.indexOf(question.actionsToPosticipate.last())
        val indiceInizio2 = question.originalPlan.actions.indexOf(question.actionsToAnticipate.first())
        val indiceFine2 = question.originalPlan.actions.indexOf(question.actionsToAnticipate.last())
        println("indici $indiceInizio $indiceFine $indiceInizio2 $indiceFine2")

        val newList = question.originalPlan.actions.subList(0, indiceInizio).toMutableList()
            .also { it.addAll(question.actionsToAnticipate) }
            .also {
                it.addAll(
                    question.originalPlan.actions.subList(
                        indiceFine + 1,
                        indiceInizio2
                    )
                )
            }
            .also { it.addAll(question.actionsToPosticipate) }
            .also {
                it.addAll(
                    question.originalPlan.actions.subList(
                        indiceFine2 + 1,
                        question.originalPlan.actions.size
                    )
                )
            }
        println("new list $newList")

        for (action in newList) {
            fluents.add(createNewGroundFluent(action, createNewPredicate(action, "traversed_")))
        }
        println(fluents)
        val newoperatorlist = mutableSetOf<Action>()
        for (action in newList) {
            if (!newoperatorlist.map {
                it.name.filter { char ->
                    char.isLetter()
                }
            }.equals(action.name)
            ) {
                newoperatorlist.add(
                    createNewAction(
                        ExplanationUtils.findAction(action, question.problem.domain.actions),
                        createNewFluent(action, createNewPredicate(action, "traversed_"))
                    )
                )
            }
        }
        println(newoperatorlist)
        val hDomain = Domain.of(
            question.problem.domain.name,
            predicates.also { it.addAll(question.problem.domain.predicates) }.toSet(),
            newoperatorlist,
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
        // possibile problema sul refresh dei fluent
        println(hProblem)

        val hPlan = BlockWorldDomain.Planners.stripsPlanner.plan(hProblem).first()

        val explanation = ContrastiveExplanation.of(question.originalPlan, hPlan, question.actionsToAnticipate.first())

        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            Plan.of(listOf(pickD, stackDC, pickB, stackBA)),
            question.actionsToAnticipate.first()
        )
        explanation shouldBe contrastiveExplanation
    }
}
