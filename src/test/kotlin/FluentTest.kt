import impl.FluentImpl
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import resources.TestUtils
import resources.TestUtils.fluentEmpty
import resources.TestUtils.fluentNotEmpty
import resources.TestUtils.getRandomInt
import resources.TestUtils.name
import resources.TestUtils.predicateEmpty
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.variableNotEmpty

class FluentTest {
    @Before
    fun init() {
        size = getRandomInt(5, 10)
        name= TestUtils.getRandomString(size)
        fluentNotEmpty = FluentImpl(name, List<Value>(size){ variableNotEmpty}, predicateNotEmpty, true)
    }

    @Test
    fun testEmptyCreation() {
        fluentEmpty.name.isEmpty() shouldBe true
        fluentEmpty.args.isEmpty() shouldBe true
        (fluentEmpty.instanceOf == predicateEmpty) shouldBe true
        fluentEmpty.isNegated shouldBe false
    }
    @Test
    fun testNotEmptyCreation() {
        fluentNotEmpty.name shouldBe name
        fluentNotEmpty.args.isEmpty() shouldBe false
        fluentNotEmpty.args.size shouldBe size
        fluentNotEmpty.args.forEach{it shouldBe variableNotEmpty}
        (fluentNotEmpty.instanceOf == predicateNotEmpty) shouldBe true
        fluentNotEmpty.isNegated shouldBe true
    }
}