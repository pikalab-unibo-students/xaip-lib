import io.kotest.matchers.shouldBe
import it.unibo.tuprolog.core.Substitution
import kotlin.test.Test
import resources.TestUtils.fluentEmpty
import resources.TestUtils.fluentNotEmpty
import resources.TestUtils.getRandomString
import resources.TestUtils.name
import resources.TestUtils.predicateEmpty
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.substitution
import resources.TestUtils.variableNotEmpty
import kotlin.test.assertFailsWith

class FluentTest {

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
        fluentNotEmpty.args.forEach { it shouldBe variableNotEmpty }
        (fluentNotEmpty.instanceOf == predicateNotEmpty) shouldBe true
        fluentNotEmpty.isNegated shouldBe true
    }
    @Test
    fun testCommonBehaviour(){
        fluentNotEmpty.match(fluentEmpty) shouldBe false
        fluentNotEmpty.match(fluentNotEmpty) shouldBe true
        fluentNotEmpty.isGround shouldBe false
        fluentNotEmpty.not().isNegated shouldBe false
        fluentNotEmpty.apply(substitution) shouldBe fluentNotEmpty //se non sono uguali resta quella originale why
    }

    @Test
    fun testMgu(){
        val variable=Variable.of(getRandomString(size))
        val fluent:Fluent =
            Fluent.of(name,
                List<Value>(size){ variable},
                predicateNotEmpty, false)

        fluent.mostGeneralUnifier(fluentNotEmpty) shouldBe
                VariableAssignment.of(variable, variableNotEmpty)

        fluentNotEmpty.mostGeneralUnifier(fluentNotEmpty) shouldBe Substitution.empty()
    }

    @Test
    fun testNotUnifiableFluent() {
        assertFailsWith(
            exceptionClass=NotUnifiableException::class,
            message="No exception found",
            block={fluentNotEmpty.mostGeneralUnifier(fluentEmpty) shouldBe Substitution.failed()}
        )
    }
}