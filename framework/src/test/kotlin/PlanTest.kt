import domain.BlockWorldDomain.Plans
import domain.BlockWorldDomain.operators
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.planEmpty
import resources.TestUtils.planNotEmpty

class PlanTest : AnnotationSpec() {
    @Test
    fun testEmptyCreation() {
        planEmpty.operators.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmpty() {
        planNotEmpty.operators.isNotEmpty() shouldBe true
        planNotEmpty.operators.size shouldBe 1
        planNotEmpty.operators.forEach { it shouldBe Operator.of(actionNotEmpty) }
    }

    @Test
    fun testPlanObjectWorksAsExpected() {
        Plans.emptyPlan.operators.isEmpty() shouldBe true

        Plans.basicPlan.operators.isNotEmpty() shouldBe true
        Plans.basicPlan.operators.forEach { it shouldBeIn operators }
    }
}
