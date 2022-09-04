import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.BlockWorldDomain.Values
import resources.BlockWorldDomain.name
import resources.BlockWorldDomain.objEmpty
import resources.BlockWorldDomain.objNotEmpty
import resources.BlockWorldDomain.objects
import resources.BlockWorldDomain.substitution

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
    fun testCommonPropertiesObjectEmpty() {
        objEmpty.apply(substitution) shouldBe objEmpty
        objEmpty.isGround shouldBe true
    }

    @Test
    fun testCommonPropertiesObjectNotEmpty() {
        objNotEmpty.apply(substitution) shouldBe objNotEmpty
        objNotEmpty.isGround shouldBe true
    }

    @Test
    fun testObjectTestUtilObjectWorksAsExpected() {
        Values.arm shouldBeIn objects
        Values.arm.isGround shouldBe true
    }

    @Test
    fun testObjectTestUtilObjectVariableAssignmentWorksAsExpected() {
        Values.arm.apply(VariableAssignment.of(Values.X, Values.X)) shouldBe Values.arm
        Values.arm.apply(VariableAssignment.of(Values.X, Values.floor)) shouldBe Values.arm
    }
}
