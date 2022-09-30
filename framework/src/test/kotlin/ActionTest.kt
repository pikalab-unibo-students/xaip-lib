import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.actionEmpty
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.name
import resources.TestUtils.type1
import resources.domain.BlockWorldDomain.Actions
import resources.domain.BlockWorldDomain.actions
import resources.domain.BlockWorldDomain.types
import resources.domain.BlockWorldDomain.variables

class ActionTest : AnnotationSpec() {

    @Test
    fun testEmptyCreation() {
        actionEmpty.name.isEmpty() shouldBe true
        actionEmpty.parameters.isEmpty() shouldBe true
        actionEmpty.preconditions.isEmpty() shouldBe true
        actionEmpty.effects.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmptyCreation() {
        actionNotEmpty.name shouldBe name
        actionNotEmpty.parameters.isEmpty() shouldNotBe true
        actionNotEmpty.parameters.forEach { it.value shouldBe type1 }
        actionNotEmpty.preconditions.isEmpty() shouldNotBe true
        actionNotEmpty.effects.isEmpty() shouldNotBe true
    }

    @Test
    fun testActionObjectWorksAsExpected() {
        Actions.pick shouldBeIn actions

        Actions.pick.parameters.isEmpty() shouldNotBe true
        Actions.pick.parameters.forEach { it.key shouldBeIn variables }
        Actions.pick.parameters.forEach { it.value shouldBeIn types }

        Actions.pick.preconditions.isEmpty() shouldNotBe true
        Actions.pick.effects.isEmpty() shouldNotBe true
    }
}
