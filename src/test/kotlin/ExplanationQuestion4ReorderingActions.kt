import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.buildHproblem
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewGroundFluent
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.stackBA
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
        stackBA,
        stackDC,
        BlockWorldDomain.Problems.stackBAstackDC,
        Plan.of(listOf(stackBA, stackDC))
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

    fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]
        this[index1] = this[index2]
        this[index2] = tmp
    }

    @Test
    fun test() {
        println("action list, original order ${question.originalPlan.actions}")
        question.originalPlan.actions.toMutableList().swap(
            question.originalPlan.actions.indexOf(question.action1),
            question.originalPlan.actions.indexOf(question.action2)
        )
        println("action list, updated order ${question.originalPlan.actions}")

        val predicates = mutableListOf<Predicate>()
        val fluents = mutableListOf<Fluent>()

        for (action in question.originalPlan.actions) {
            val predicate1 = newPredicate1(action, "ordered_")
            val predicate2 = newPredicate1(action, "traversed_")
            predicates.add(predicate1)
            predicates.add(predicate2)
            fluents.add(createNewGroundFluent(action as Operator, predicate1))
            fluents.add(createNewGroundFluent(action, predicate2))
            fluents.add(createNewFluent(action, predicate1))
            fluents.add(createNewFluent(action, predicate2))
        }
        println("PredicateSet $predicates")
        println("FluentSet $fluents")
        val indexAction1 = fluents.indexOf(
            fluents.first {
                it.name.startsWith("ordered") && it.args == question.action1.args
            }
        )
        val indexAction2 = fluents.indexOf(
            fluents.first {
                it.name.startsWith("ordered") && it.args == question.action2.args
            }
        )
        println("Index1 $indexAction1")
        println("Index2 $indexAction2")
        println(
            "Action1 preconditions ${fluents.subList(0, indexAction1)
                .filter { it.name.contains("ordered") }.toSet()}"
        )
        println(
            "Action2 preconditions ${fluents.subList(0, indexAction2)
                .filter { it.name.contains("ordered") }.toSet()}"
        )

        val newAction1 = createNewAction1(
            question.action1,
            fluents.subList(0, indexAction1).filter { it.name.contains("ordered") }.toSet(),
            fluents.first {
                it.name.startsWith("traversed") && it.args == question.action1.args
            }
        )

        val newAction1 = createNewAction1(
            question.action1,
            fluents.subList(0, indexAction1).filter { it.name.contains("ordered") }.toSet(),
            fluents.first {
                it.name.startsWith("traversed") && it.args == question.action1.args
            }
        )
        println("new action1 $newAction1")

        val newAction2 = createNewAction1(
            question.action2,
            fluents.subList(0, indexAction2).filter { it.name.contains("ordered") }.toSet(),
            fluents.first {
                it.name.startsWith("traversed") && it.args == question.action2.args
            }
        )
        println("new action2: $newAction2")

        fun buildHdomain(domain: Domain, newPredicate: Set<Predicate>, newAction: Action, newAction2: Action) =
            Domain.of( // domain extended
                name = domain.name,
                predicates = (newPredicate).toMutableSet().also { it.addAll(domain.predicates) },
                actions = mutableSetOf(newAction).also {
                    domain.actions.map { oldAction ->
                        if (oldAction.name != newAction.name.filter { char ->
                            char.isLetter()
                        }
                        ) it.add(oldAction)
                    }
                },//.also { it.add(newAction2) },
                types = domain.types
            )

        val hDomain = buildHdomain(question.problem.domain, predicates.toSet(), newAction1, newAction2)
        // mi sa che ci sia da switchare l'ordine dei goal
        val hProblem = buildHproblem(hDomain, question.problem, fluents[indexAction1], null, true)
        val hPlan = BlockWorldDomain.Planners.stripsPlanner.plan(hProblem).first()

        val explanation: ExplanationUtils.ContrastiveExplanation =
            ExplanationUtils.buildExplanation(question.originalPlan, hPlan, question.action1)

        val contrastiveExplanation = ExplanationUtils.ContrastiveExplanation(
            question.originalPlan,
            Plan.of(listOf(stackDC, stackBA)),
            question.action1,
            setOf(),
            setOf(),
            question.originalPlan.actions.map { it as Operator }.toSet()
        )
        explanation shouldBe contrastiveExplanation
    }
}
