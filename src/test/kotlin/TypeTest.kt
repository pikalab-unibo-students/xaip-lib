import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain.Types

class TypeTest : AnnotationSpec() {
    @Test
    fun testConstructor() {
        Type.of("anything") shouldBe Types.anything
    }

    @Test
    fun testToString() {
        Types.anything.name shouldBe "anything"
        Types.anything.superType?.name.toString() shouldBe "null"

        Types.strings.name shouldBe "strings"
        Types.strings.superType?.name.toString() shouldBe "anything"
        Types.strings.superType shouldBe Types.anything

        Types.locations.name shouldBe "locations"
        Types.locations.superType?.name.toString() shouldBe "strings"
        Types.locations.superType shouldBe Types.strings

        Types.blocks.name shouldBe "blocks"
        Types.blocks.superType?.name.toString() shouldBe "strings"
        Types.blocks.superType shouldBe Types.strings

        Types.numbers.name shouldBe "numbers"
        Types.numbers.superType?.name.toString() shouldBe "anything"
        Types.numbers.superType shouldBe Types.anything
    }
}
