import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.Operands

class OperandTest : AnnotationSpec() {
    @Test
    fun testCreation() {
        Operands.not.name shouldBe "not"
        Operands.and.name shouldBe "and"
        Operands.or.name shouldBe "or"
    }
}
