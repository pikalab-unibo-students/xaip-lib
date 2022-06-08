import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.InternalPlatformDsl.toArray
import resources.TestUtils.Values
import resources.TestUtils.variables
import resources.TestUtils.name

class VariableTest : AnnotationSpec() {
    private val localName = name
    private val localVariableEmpty = Variable.of("")
    private val localVariableNotEmpty = Variable.of(localName)
    private fun removePostfix(string: String) = string.replace("_[0-9]".toRegex(), "")

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
        Values.X shouldBeIn  variables
        Values.X.name shouldBeIn variables.map { it.name }
    }

}