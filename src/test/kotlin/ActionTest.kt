import impl.ActionImpl
import impl.FluentImpl
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Before
import resources.Res
import resources.Res.actionEmpty
import resources.Res.actionNotEmpty
import resources.Res.effectNotEmpty
import resources.Res.fluentNotEmpty
import resources.Res.getRandomInt
import resources.Res.name
import resources.Res.predicateNotEmpty
import resources.Res.size
import resources.Res.type1
import resources.Res.variableNotEmpty
import kotlin.test.Test

class ActionTest {
    @Before
    fun init() {
        size = getRandomInt(5, 10)
        name= Res.getRandomString(size)
        fluentNotEmpty = FluentImpl(name, List<Value>(size){ variableNotEmpty }, predicateNotEmpty, true)
        actionNotEmpty = ActionImpl(name, mapOf(Res.variableNotEmpty to type1), setOf(fluentNotEmpty), setOf(effectNotEmpty))
    }


    @Test
    fun testEmptyCreation() {
        actionEmpty.name shouldBe ""
        actionEmpty.parameters.isEmpty() shouldBe true
        actionEmpty.preconditions.isEmpty() shouldBe true
        actionEmpty.effects.isEmpty() shouldBe true
    }
    @Test
    fun testNotEmptyCreation() {
        actionNotEmpty.name shouldBe name
        actionNotEmpty.parameters.isEmpty() shouldNotBe true
        actionNotEmpty.parameters.forEach{it.value shouldBe type1}
        actionNotEmpty.preconditions.isEmpty() shouldNotBe true
        actionNotEmpty.effects.isEmpty() shouldNotBe true
    }
}