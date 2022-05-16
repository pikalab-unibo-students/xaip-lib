import impl.res.toTerm
import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.Res.variableEmpty
import resources.Res.variableNotEmpty
import resources.Res.name

import it.unibo.tuprolog.core.*

class VarTest {
    /**
     * Supports formatting a term in user-friendly Prolog syntax
     */
    private val formatter = TermFormatter.prettyVariables()

    @Test
    fun varEmptyCreation(){
        formatter.format(variableEmpty.toTerm()).replace("`", "") shouldBe ""
    }
    @Test
    fun varNotEmptyCreation(){
        formatter.format(variableNotEmpty.toTerm()) shouldBe name
    }
}