import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.Res.variableEmpty
import resources.Res.variableNotEmpty
import resources.Res.name
import kotlin.test.assertEquals

class VarTest {
    @Test
    fun varEmptyCreation(){
        assertEquals ( variableEmpty.name, "")
    }
    @Test
    fun varNotEmptyCreation(){
        variableNotEmpty.name shouldBe name
    }
}