import io.kotest.matchers.shouldBe
import it.unibo.tuprolog.core.toVar
import org.junit.Test
import resources.Res.variableEmpty
import resources.Res.variableNotEmpty
import resources.Res.name
import kotlin.test.assertEquals

class VarTest {
    @Test
    fun varEmptyCreation(){
         variableEmpty.name.isEmpty() shouldBe true
    }
    @Test
    fun varNotEmptyCreation(){
        variableNotEmpty.name shouldBe name
    }
}