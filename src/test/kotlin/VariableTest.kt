import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Values
import resources.TestUtils.name
import resources.TestUtils.removePostfix
import resources.TestUtils.variables

class VariableTest : AnnotationSpec() {
    private val localName = name
    private val localVariableEmpty = Variable.of("")
    private val localVariableNotEmpty = Variable.of(localName)

    @Test
    fun basicBehavior() {
        localVariableEmpty shouldNotBe Variable.of("")
        localVariableNotEmpty shouldNotBe Variable.of(localName)
    }

    @Test
    fun freshVariableBehavior() {
        removePostfix(localVariableEmpty.name) shouldBe ""
        localVariableEmpty.name shouldNotBe ""
        removePostfix(localVariableEmpty.name) shouldBe removePostfix(localVariableEmpty.name)

        removePostfix(localVariableNotEmpty.name) shouldBe localName
        localVariableNotEmpty.name shouldNotBe localName
        removePostfix(localVariableNotEmpty.name) shouldBe removePostfix(localVariableNotEmpty.name)
    }

    @Test
    fun testVariableObjectWorksAsExpected() {
        Values.X shouldBeIn variables
        Values.X.name shouldBeIn variables.map { it.name }
    }

    @Test
    fun testRefreshVariableWorksAsExpected() {
        localVariableNotEmpty shouldBe localVariableNotEmpty
        localVariableNotEmpty shouldNotBe localVariableNotEmpty.refresh()
    }
}
