import explanation.Question1
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.ContrastiveExplanation
import resources.ExplanationUtils.buildHdomain
import resources.ExplanationUtils.buildHproblem
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewGroundFluent
import resources.ExplanationUtils.createNewPredicate
import resources.ExplanationUtils.findAction
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class ExplanationQuestion1AddAction2aPlan : AnnotationSpec() {
    // 1.“Why is action A not used in the plan, rather than being used?” //add action to a state

    /*
     * Idea:
     *          1. creo un predicate (+ fluent) che mi indichi se un'azione è stata eseguita.
     *          2. recupero l'azione che l'utente vorrebbe inserire
     *          3. la estendo in modo tale da includere il nuovo fluent tra gli effetti positivi
     *          4. aggiorno il Domain per includere la nuova azione
     *          5. aggiunto il nuovo fluent corrisponde all'azione ai goal
     * Problematiche: gestione della sequenza deipaini in output, quali, quanti ne ritorno
     */
    @Test
    fun testQuestion1() {
        val question = ExplanationUtils.Question1(
            pickA,
            Problems.armNotEmpty,
            Plan.of(listOf(pickB))
        )
        // 1.
        val newPredicate = createNewPredicate(question.actionToAddOrToRemove, "has_done_")

        val newGroundFluent = createNewGroundFluent(question.actionToAddOrToRemove, newPredicate)
        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate)

        // 2.
        val oldAction =
            findAction(question.actionToAddOrToRemove, question.problem.domain.actions)

        // 3.
        val newAction = createNewAction(oldAction, newFluent)

        // 4.
        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)

        // 5.
        val hProblem = buildHproblem(hDomain, question.problem, newGroundFluent, null)
        val hPlan = stripsPlanner.plan(hProblem).first()

        val explanation: ContrastiveExplanation =
            ContrastiveExplanation.of(question.originalPlan, hPlan, question.actionToAddOrToRemove)

        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            hPlan,
            question.actionToAddOrToRemove
        )
        explanation shouldBe contrastiveExplanation
    }

    @Test
    fun testQuestion2() {
        val question = ExplanationUtils.Question1(
            pickC,
            Problems.armNotEmpty,
            Plan.of(listOf(pickB))
        )
        val newPredicate = createNewPredicate(question.actionToAddOrToRemove, "has_done_")
        val newGroundFluent = createNewGroundFluent(question.actionToAddOrToRemove, newPredicate)
        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate)
        val notGroundAction =
            findAction(question.actionToAddOrToRemove, question.problem.domain.actions)
        val newAction = createNewAction(notGroundAction, newFluent)
        val newGroundAction = Operator.of(newAction).apply(VariableAssignment.of(Values.X, Values.c))

        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)
        val hProblem = buildHproblem(hDomain, question.problem, newGroundFluent, null)
        val hplan = stripsPlanner.plan(hProblem).first {
            it.actions.contains(newGroundAction)
        }

        val explanation: ContrastiveExplanation =
            ContrastiveExplanation.of(question.originalPlan, hplan, question.actionToAddOrToRemove)

        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            hplan,
            question.actionToAddOrToRemove
        )
        explanation shouldBe contrastiveExplanation
    }

    @Test
    fun testQuestionNew() {
        val q1 = Question1(
            Problems.armNotEmpty,
            Plan.of(listOf(pickB)),
            pickA
        )

        val hPlan = stripsPlanner.plan(q1.buildHproblem()).first()

        val explanation: ContrastiveExplanation =
            ContrastiveExplanation.of(q1.plan, hPlan, q1.focus)

        val contrastiveExplanation = ContrastiveExplanation.of(
            q1.plan,
            hPlan,
            q1.focus
        )
        explanation shouldBe contrastiveExplanation
    }
}
