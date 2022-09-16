import io.kotest.core.spec.style.AnnotationSpec
import resources.ExplanationUtils
import resources.ExplanationUtils.buildExplanation
import resources.ExplanationUtils.buildHproblem
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators.pickB
import resources.domain.BlockWorldDomain.Operators.pickC
import resources.domain.BlockWorldDomain.Operators.pickD
import resources.domain.BlockWorldDomain.Operators.stackAB
import resources.domain.BlockWorldDomain.Operators.stackBD
import resources.domain.BlockWorldDomain.Operators.stackCA
import resources.domain.BlockWorldDomain.Operators.stackDB
import resources.domain.BlockWorldDomain.Planners.stripsPlanner
import resources.domain.BlockWorldDomain.Problems
import resources.domain.BlockWorldDomain.States

class ExplanationQuestion4ReorderingActions : AnnotationSpec() {
    // 4.“Why is action A used before/after action B (rather than after/before)?” // reordering actions
    /*val contrastiveExplanation = ExplanationUtils.ContrastiveExplanation(
        questionAddCD.originalPlan,
        hplan,
        questionAddCD.actionToAddOrToRemove,
        setOf(newActionGrounded),
        setOf(questionAddCD.actionToAddOrToRemove),
        setOf(BlockWorldDomain.Operators.stackCB)
    )

     */

    // explanation shouldBe contrastiveExplanation
}
