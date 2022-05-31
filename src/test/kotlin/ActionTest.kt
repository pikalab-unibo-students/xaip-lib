import impl.ActionImpl
import impl.FluentImpl
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Before
import resources.TestUtils
import resources.TestUtils.actionEmpty
import resources.TestUtils.actionNotEmpty
import resources.TestUtils.effectNotEmpty
import resources.TestUtils.fluentNotEmpty
import resources.TestUtils.getRandomInt
import resources.TestUtils.name
import resources.TestUtils.objNotEmpty
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.substitution
import resources.TestUtils.type1
import resources.TestUtils.variableNotEmpty
import kotlin.test.Test

class ActionTest {
    var localName=""
    @Before
    fun init() {
        size = getRandomInt(5, 10)
        localName= TestUtils.getRandomString(size)
        fluentNotEmpty = FluentImpl(name, List<Value>(size){ variableNotEmpty }, predicateNotEmpty, true)
        actionNotEmpty = ActionImpl(name, mapOf(variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(effectNotEmpty))
    }


    @Test
    fun testEmptyCreation() {
        actionEmpty.name.isEmpty() shouldBe true
        actionEmpty.parameters.isEmpty() shouldBe true
        actionEmpty.preconditions.isEmpty() shouldBe true
        actionEmpty.effects.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmptyCreation() {
        val substitution2=
            VariableAssignment.of(Variable.of("prova"),
                objNotEmpty)

        actionNotEmpty.name shouldBe name
        actionNotEmpty.parameters.isEmpty() shouldNotBe true
        actionNotEmpty.parameters.forEach{it.value shouldBe type1}
        actionNotEmpty.preconditions.isEmpty() shouldNotBe true
        actionNotEmpty.effects.isEmpty() shouldNotBe true
        //Boh sta cosa del perché non cambia mai nulla non mi è chiarissima
        actionNotEmpty.apply(substitution) shouldBe actionNotEmpty
        actionNotEmpty.apply(substitution2) shouldBe actionNotEmpty
    }
}