import impl.res.toTerm
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import resources.TestUtils.variableEmpty
import resources.TestUtils.variableNotEmpty
import resources.TestUtils.name

import it.unibo.tuprolog.core.*

class VarTest {
    /**
     * Supports formatting a term in user-friendly Prolog syntax
     */
    private val formatter = TermFormatter.prettyVariables()

    @Test
    fun varEmptyCreation(){
        variableEmpty.name.replace("_[0-9]".toRegex(), "") shouldBe ""
    }
    @Test
    fun varNotEmptyCreation(){
        variableNotEmpty.name.replace("_[0-9]".toRegex(), "") shouldBe name
    }
}