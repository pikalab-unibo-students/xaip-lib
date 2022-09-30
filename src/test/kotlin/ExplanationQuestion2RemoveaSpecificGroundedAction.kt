import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.ExplanationUtils
import resources.ExplanationUtils.ContrastiveExplanation
import resources.ExplanationUtils.Question1
import resources.ExplanationUtils.buildHdomain
import resources.ExplanationUtils.buildHproblem
import resources.ExplanationUtils.createNewAction
import resources.ExplanationUtils.createNewFluent
import resources.ExplanationUtils.createNewGroundFluent
import resources.ExplanationUtils.createNewPredicate
import resources.domain.BlockWorldDomain.Operators.pickA
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.Values

class ExplanationQuestion2RemoveaSpecificGroundedAction : AnnotationSpec() {
    // 2. Why is action A used in state, rather not being used? // remove specific grounded action

    /*
 * Idea:
 *          1. creo un predicate (+ fluent) che mi indichi se un'azione non è stata eseguita.
 *          2. recupero l'azione che l'utente vorrebbe inserire
 *          3. la estendo in modo tale da includere il nuovo fluent tra gli effetti negativi
 *          4. aggiorno il Domain per includere la nuova azione
 *          5. aggiunto il nuovo fluent corrisponde all'azione ai goal
 * Problematiche: gestione della sequenza deipaini in output, quali, quanti ne ritorno
 */
    @Test
    fun testQuestion2() {
        val question = Question1(
            pickA,
            Problems.armNotEmpty,
            Plan.of(listOf(pickA))
        )

        // 1.
        val newPredicate = createNewPredicate(question.actionToAddOrToRemove, "not_done_", true)
        val newFluent = createNewFluent(question.actionToAddOrToRemove, newPredicate)
        val newGroundFluent = createNewGroundFluent(question.actionToAddOrToRemove, newPredicate)

        // 2.
        val oldAction =
            ExplanationUtils.findAction(question.actionToAddOrToRemove, question.problem.domain.actions)

        // 3.
        val newAction = createNewAction(oldAction, newFluent, true) // new action
        val newGroundAction = Operator.of(newAction).apply(VariableAssignment.of(Values.X, Values.b))

        // 4.
        val hDomain = buildHdomain(question.problem.domain, newPredicate, newAction)
        // 5.
        val hProblem = buildHproblem(hDomain, question.problem, newGroundFluent, null, true)
        val hplan = stripsPlanner.plan(hProblem).first()

        val explanation = ContrastiveExplanation.of(question.originalPlan, hplan, question.actionToAddOrToRemove)

        val contrastiveExplanation = ContrastiveExplanation.of(
            question.originalPlan,
            Plan.of(listOf(newGroundAction)),
            question.actionToAddOrToRemove
        )

        explanation shouldBe contrastiveExplanation
    }
}
