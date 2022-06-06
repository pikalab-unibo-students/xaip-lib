import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.getRandomInt
import resources.TestUtils.getRandomString
import kotlin.test.Test

class VariableTest {
    private val localName = getRandomString(getRandomInt(3, 10))
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


}