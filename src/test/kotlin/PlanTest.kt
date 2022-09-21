import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.planEmpty
import resources.TestUtils.planNotEmpty
import resources.domain.BlockWorldDomain.Plans
import resources.domain.BlockWorldDomain.operators

class PlanTest : AnnotationSpec() {
    @Test
    fun testEmptyCreation() {
        planEmpty.actions.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmpty() {
        planNotEmpty.actions.isNotEmpty() shouldBe true
        planNotEmpty.actions.size shouldBe 1
        planNotEmpty.actions.forEach { it shouldBe Operator.of(actionNotEmpty) }
    }

    @Test
    fun testPlanObjectWorksAsExpected() {
        Plans.emptyPlan.actions.isEmpty() shouldBe true

        Plans.basicPlan.actions.isNotEmpty() shouldBe true
        Plans.basicPlan.actions.forEach { it shouldBeIn operators }
    }
}
