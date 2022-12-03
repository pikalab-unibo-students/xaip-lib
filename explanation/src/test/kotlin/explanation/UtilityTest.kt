package explanation
import domain.BlockWorldDomain.Actions
import domain.BlockWorldDomain.Operators
import explanation.utils.retrieveAction
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class UtilityTest : AnnotationSpec() {
    @Test
    fun retrieveActionFromSet() {
        val set = setOf(Actions.pick, Actions.putdown, Actions.unstack, Actions.stack)

        set.retrieveAction(Operators.pickA) shouldBe Actions.pick
        set.retrieveAction(Operators.putdownA) shouldBe Actions.putdown
        set.retrieveAction(Operators.unstackAB) shouldBe Actions.unstack
        set.retrieveAction(Operators.stackAB) shouldBe Actions.stack
    }
}
