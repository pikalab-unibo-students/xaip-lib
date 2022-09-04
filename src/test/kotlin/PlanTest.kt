import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.BlockWorldDomain.Plans
import resources.BlockWorldDomain.actionNotEmpty
import resources.BlockWorldDomain.actions
import resources.BlockWorldDomain.planEmpty
import resources.BlockWorldDomain.planNotEmpty

class PlanTest : AnnotationSpec() {
    @Test
    fun testEmptyCreation() {
        planEmpty.actions.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmpty() {
        planNotEmpty.actions.isNotEmpty() shouldBe true
        planNotEmpty.actions.size shouldBe 1
        planNotEmpty.actions.forEach { it shouldBe actionNotEmpty }
    }

    @Test
    fun testPlanObjectWorksAsExpected() {
        Plans.emptyPlan.actions.isEmpty() shouldBe true

        Plans.dummyPlan.actions.isNotEmpty() shouldBe true
        Plans.dummyPlan.actions.forEach { it shouldBeIn actions }
    }
}
