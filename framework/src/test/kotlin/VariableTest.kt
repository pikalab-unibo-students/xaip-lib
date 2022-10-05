import domain.BlockWorldDomain.Values
import domain.BlockWorldDomain.variables
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.name

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
        localVariableEmpty.name.filter { it.isUpperCase() } shouldBe ""
        localVariableEmpty.name shouldNotBe ""
        localVariableEmpty.name.filter { it.isUpperCase() } shouldBe localVariableEmpty.name.filter { it.isUpperCase() }

        localVariableNotEmpty.name.filter { it.isUpperCase() } shouldBe localName
        localVariableNotEmpty.name shouldNotBe localName
        localVariableNotEmpty.name.filter { it.isUpperCase() } shouldBe
            localVariableNotEmpty.name.filter { it.isUpperCase() }
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
