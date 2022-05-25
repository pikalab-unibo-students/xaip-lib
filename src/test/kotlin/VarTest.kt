import io.kotest.matchers.shouldBe
import kotlin.test.Test
import resources.TestUtils.name
import resources.TestUtils.variableEmpty
import resources.TestUtils.variableNotEmpty

class VarTest {
    @Test
    fun varEmptyCreation(){
        variableEmpty.name.replace("_[0-9]".toRegex(), "") shouldBe ""
    }
    @Test
    fun varNotEmptyCreation(){
        variableNotEmpty.name.replace("_[0-9]".toRegex(), "") shouldBe name
    }
}