import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.name
import resources.TestUtils.objEmpty
import resources.TestUtils.objNotEmpty
import resources.TestUtils.substitution
import resources.TestUtils.Values
import resources.TestUtils.objects

class ObjectTest : AnnotationSpec() {
    @Test
    fun testEmptyCreation() {
        objEmpty.representation.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmptyCreation() {
        objNotEmpty.representation.replace("_[0-9]".toRegex(), "") shouldBe name
    }

    @Test
    fun testCommonProperties() {
        objEmpty.apply(substitution) shouldBe objEmpty
        objEmpty.isGround shouldBe true

        objNotEmpty.apply(substitution) shouldBe objNotEmpty
        objNotEmpty.isGround shouldBe true
    }

    @Test
    fun testObjectTestUtilObjectWorksAsExpected() {
        Values.arm shouldBeIn objects

        Values.arm.apply(VariableAssignment.of(Values.X, Values.X)) shouldBe Values.arm
        Values.arm.apply(VariableAssignment.of(Values.X, Values.floor)) shouldBe Values.arm

        Values.arm.isGround shouldBe true
    }
}