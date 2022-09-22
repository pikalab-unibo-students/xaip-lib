
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

    /*
     * Idea:
     * 1. creo tutti i nuovi predicati da aggiungere al Dominio
     * 2. creo il DAG semplicemente riordinando il piano secondo le scelte dell'utente
     *      nota: la funzione fa schifo e certamente può essere implementata meglio
     * 3. creo i nuovi fluent da aggiungere al goal
     * 4. creo le nuove azioni aggiungenedo un nuovo fluent come effetto
     * 5. creo il nuovo dominio aggiungendo i predicati (creati nel punto 1)
     *      e le azioni (create nel punto 4)
     * 6. creo il nuovo problema dandogli il nuovo dominio e aggiornando il goal
     *      in modo che includa i fluent ground corrispondenti all'esecuzione
     *      delle azioni del piano che voglio in output
     *      Note: si basa sul fatto che tutti i fluent del goal devono essere nello stato finale,
     *             gioca sull'ordine dei fluent del goal pushand prima i fluent che voglio
     *             siano soddisfatti per primi per evitare di fare incasinare il planner.
     *             L'ordine lo so perché l'utente mi dice che piano si aspetta in output.
     */
    @Test
    fun test() {
        val predicates = mutableListOf<Predicate>()
        val fluents = mutableListOf<Fluent>()
        val newActions = mutableSetOf<Action>()
        // 1
        for (action in question.originalPlan.actions) {
            val predicate = createNewPredicate(action, "traversed_")
            predicates.add(predicate)
        }
        // 2
        val reorderedPlan = reorderPlan(
            question.originalPlan,
            question.actionsToPosticipate,
            question.actionsToAnticipate
        )
        // 3
        for (action in reorderedPlan) {
            fluents.add(createNewGroundFluent(action, createNewPredicate(action, "traversed_")))
        }
        // 4
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
        // 5
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
        var newPickD = Operator.of(newActions.first())
        newPickD = newPickD.apply(VariableAssignment.of(Values.X, Values.d))
        var newPickB = Operator.of(newActions.first())
        newPickB = newPickB.apply(VariableAssignment.of(Values.X, Values.b))
        var newStackDC = Operator.of(newActions.last())
        newStackDC = newStackDC.apply(VariableAssignment.of(Values.X, Values.d))
        newStackDC = newStackDC.apply(VariableAssignment.of(Values.Y, Values.c))
        var newStackBA = Operator.of(newActions.last())
        newStackBA = newStackBA.apply(VariableAssignment.of(Values.X, Values.b))
        newStackBA = newStackBA.apply(VariableAssignment.of(Values.Y, Values.a))
        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            Plan.of(listOf(newPickD, newStackDC, newPickB, newStackBA)),
            question.actionsToAnticipate.first()
        )
        explanation shouldBe contrastiveExplanation
    }
}
