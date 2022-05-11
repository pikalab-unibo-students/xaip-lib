import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.res.variableEmpty
import resources.res.variableNotEmpty
import resources.res.nameGC
import kotlin.test.assertEquals

class VarTest {
    @Test
    fun varEmptyCreation(){
        assertEquals ( variableEmpty.name, "")
    }
    @Test
    fun varNotEmptyCreation(){
        variableNotEmpty.name shouldBe nameGC
    }
}