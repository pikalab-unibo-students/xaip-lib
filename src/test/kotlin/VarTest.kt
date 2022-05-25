import impl.res.toTerm
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import resources.TestUtils.variableEmpty
import resources.TestUtils.variableNotEmpty
import resources.TestUtils.name

import it.unibo.tuprolog.core.*

class VarTest {
    private val variableEmpty1 = Variable.of("")
    private val variableNotEmpty1 = Variable.of(name)

    @Test
    fun varEmptyCreation(){
        variableEmpty1.name.replace("_[0-9]".toRegex(), "") shouldBe ""
    }
    @Test
    fun varNotEmptyCreation(){
        variableNotEmpty1.name.replace("_[0-9]".toRegex(), "") shouldBe name
    }
}