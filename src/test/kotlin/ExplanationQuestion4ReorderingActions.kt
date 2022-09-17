import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils
import resources.ExplanationUtils.createNewFluent
import java.util.*

class ExplanationQuestion4ReorderingActions : AnnotationSpec() {
    // 4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions
    val question: ExplanationUtils.Question1 = TODO()
    val action1: Action
    val action2: Action
    val hPlan: Plan

    fun newPredicate1(action: Action, string: String): Predicate =
        Predicate.of(string + action.name, action.parameters.values.toList())

    fun createNewAction1(action: Action, fluent1: Fluent, fluent2: Fluent): Action {
        return Action.of(
            name = action.name + "^",
            parameters = action.parameters,
            preconditions = mutableSetOf(fluent1).also {
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
        question.originalPlan.actions.toMutableList().swap(
            question.originalPlan.actions.indexOf(action1),
            question.originalPlan.actions.indexOf(action2)
        )
        val predicateSet = mutableSetOf<Predicate>()
        val fluentSet = mutableSetOf<Fluent>()

        for (action in question.originalPlan.actions) {
            val predicate1 = newPredicate1(action, "ordered_")
            val predicate2 = newPredicate1(action, "traversed_")
            predicateSet.add(predicate1)
            predicateSet.add(predicate2)
            fluentSet.add(createNewFluent(action as Operator, predicate1))
            fluentSet.add(createNewFluent(action as Operator, predicate2))
        }
        println("PredicateSet $predicateSet")
        println("FluentSet $fluentSet")

        val newAction1 = createNewAction1(
            action1,
            fluentSet.filter { it.name.contains("ordered" + action1.parameters.keys.toString())}.first(),
            fluentSet.filter { it.name.contains("traversed" + action1.parameters.keys.toString())}.first()
        )
        println("new action1 " + newAction1)

        val newAction2 = createNewAction1(
            action2,
            fluentSet.filter { it.name.contains("ordered" + action2.parameters.keys.toString()) }.first(),
            fluentSet.filter { it.name.contains("traversed" + action2.parameters.keys.toString())}.first()
        )
        println("new action2: " + newAction2)

        fun buildHdomain(domain: Domain, newPredicate: Predicate, newAction: Action, newAction2: Action) =
            Domain.of( // domain extended
                name = domain.name,
                predicates = mutableSetOf(newPredicate).also { it.addAll(domain.predicates) },
                actions = mutableSetOf(newAction).also {
                    domain.actions.map { oldAction ->
                        if (oldAction.name != newAction.name.filter { char ->
                            char.isLetter()
                        }
                        ) it.add(oldAction)
                    }
                }.also { it.add(newAction2) },
                types = domain.types
            )

        val contrastiveExplanation = ExplanationUtils.ContrastiveExplanation(
            question.originalPlan,
            hPlan,
            question.actionToAddOrToRemove,
            setOf(),
            setOf(),
            question.originalPlan.actions.map { it as Operator }.toSet()
        )
    }
    // explanation shouldBe contrastiveExplanation
}
