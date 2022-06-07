import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.actionEmpty
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.name
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.substitution
import resources.TestUtils.type1
import resources.TestUtils.variableNotEmpty

class ActionTest : AnnotationSpec() {
    private val variable = Variable.of("different value")
    private val substitution2 = VariableAssignment.of(variableNotEmpty, variable)
    private val fluent = Fluent.of(
        predicateNotEmpty, true, List<Value>(size) { variable }
    )
    private val action = Action.of(
        name,
        mapOf(variableNotEmpty to type1),
        setOf(fluent),
        setOf(Effect.of(fluent, true))
    )

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
    fun testApplyWorksAsExpected() {
        actionNotEmpty.apply(substitution) shouldBe actionNotEmpty
        actionNotEmpty.apply(substitution2) shouldBe action
    }
}