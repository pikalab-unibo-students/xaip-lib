import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test
import resources.res.nameGC
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class VarTest {
    private val variable: Var= mockkClass(Var::class){
        every{name} returns ""
    }
    private val variableNotEmpty: Var = mockkClass(Var::class){
        every{name} returns nameGC
    }

    @Test
    fun varEmptyCreation(){
        assertEquals ( variable.name, "")
    }
    @Test
    fun varNotEmptyCreation(){
        variableNotEmpty.name shouldBe nameGC
    }
}