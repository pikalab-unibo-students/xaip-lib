import dsl.problem
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import it.unibo.tuprolog.core.Scope
import resources.ExplanationUtils
import resources.ExplanationUtils.ContrastiveExplanation
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewGroundFluent
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.stackAB
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
        listOf(pickA, stackAB),
        BlockWorldDomain.Problems.stackBAstackDC,
        Plan.of(listOf(pickA, stackAB, pickD, stackDC))
    )

    fun newPredicate1(action: Action, string: String): Predicate =
        Predicate.of(string + action.name, action.parameters.values.toList())

    fun createNewAction1(action: Action, fluentInEffect: Fluent): Action {
        val refreshedFluent =
            fluentInEffect.refresh(
                Scope.of(
                    (action.preconditions.first().args.last().toString())
                    // /effects.first().fluent.args.first() as Variable).toTerm()
                )
            )
        return Action.of(
            name = action.name + "^",
            parameters = action.parameters,
            preconditions = action.preconditions,
            effects = mutableSetOf(Effect.of(refreshedFluent)).also {
                it.addAll(action.effects)
            }
        )
    }

    // @Ignore
    @Test
    fun test() {
        println("action list, original order ${question.originalPlan.actions}")

        val predicates = mutableListOf<Predicate>()
        val fluents = mutableListOf<Fluent>()

        // creare il DAG

        for (action in question.originalPlan.actions) {
            val predicate1 = newPredicate1(action, "ordered_")
            val predicate2 = newPredicate1(action, "traversed_")
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
            .also { it.addAll(question.originalPlan.actions.subList(indiceFine + 1, indiceInizio2)) }
            .also { it.addAll(question.actionsToPosticipate) }
            .also { it.addAll(question.originalPlan.actions.subList(indiceFine2 + 1, question.originalPlan.actions.size)) }
        println("new list $newList")

        for (action in newList) {
            fluents.add(createNewGroundFluent(action, newPredicate1(action, "traversed_")))
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
                        createNewFluent(action, newPredicate1(action, "traversed_"))
                    )
                )
            }
        }
        println(newoperatorlist)
        val hDomain = Domain.of(
            question.problem.domain.name,
            predicates.also { it.addAll(question.problem.domain.predicates) }.toSet(),
            newoperatorlist,
            // newoperatorlist.also { it.addAll(question.problem.domain.actions) }.toSet(),
            question.problem.domain.types
        )
        val prova: List<Fluent> = fluents.toMutableList().also { it.addAll((question.problem.goal as FluentBasedGoal).targets) }
        println(prova)
        val hProblem = Problem.of(
            hDomain,
            question.problem.objects,
            question.problem.initialState,
            // State.of(question.problem.initialState.fluents.toMutableSet().also { it.addAll(fluents) }),
            FluentBasedGoal.of(
                fluents.toMutableSet().also { it.addAll((question.problem.goal as FluentBasedGoal).targets) }
            )
        )
        // possibile problema sul refresh dei fluent
        println(hProblem)

        val plans = BlockWorldDomain.Planners.stripsPlanner.plan(hProblem)
        val hPlan = plans.first()
        val explanation = ContrastiveExplanation.of(question.originalPlan, hPlan, question.actionsToAnticipate.first())

        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            Plan.of(listOf(pickD, stackDC, pickA, stackAB)),
            question.actionsToAnticipate.first()
        )
        explanation shouldBe contrastiveExplanation
    }
}
