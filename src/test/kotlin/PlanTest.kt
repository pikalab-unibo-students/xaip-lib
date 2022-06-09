import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.planEmpty
import resources.TestUtils.planNotEmpty
import resources.TestUtils.Plans
import resources.TestUtils.actions

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
        Plans.dummyPlan.actions.forEach { it shouldBe actions }
    }
}