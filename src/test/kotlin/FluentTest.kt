import io.kotest.matchers.shouldBe
import it.unibo.tuprolog.core.Substitution
import resources.TestUtils.fluentEmpty
import resources.TestUtils.fluentNotEmpty
import resources.TestUtils.name
import resources.TestUtils.predicateEmpty
import resources.TestUtils.predicateNotEmpty
import resources.TestUtils.size
import resources.TestUtils.substitution
import resources.TestUtils.variableNotEmpty
import kotlin.test.Test
import kotlin.test.assertFailsWith

class FluentTest {
    private val variable = Variable.of("different value")
    private val fluent1: Fluent = Fluent.of(
        name,
        List<Value>(size) { variableNotEmpty },
        predicateNotEmpty, true
    )
    private val fluent2: Fluent = Fluent.of(
        name,
        List<Value>(size) { variable },
        predicateNotEmpty, true
    )
    private val substitution1 =
        VariableAssignment.of(variableNotEmpty, variable)

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
    fun testCommonBehaviour() {
        fluentNotEmpty.match(fluentEmpty) shouldBe false
        fluentNotEmpty.match(fluentNotEmpty) shouldBe true

        fluentNotEmpty.isGround shouldBe false
        fluentNotEmpty.not().isNegated shouldBe false
    }

    @Test
    fun testApplyWorksAsExpected() {
        fluent1.apply(substitution) shouldBe fluentNotEmpty
        fluent1.apply(substitution1) shouldBe fluent2
    }

    @Test
    fun testMgu() {
        fluent1.mostGeneralUnifier(fluentNotEmpty) shouldBe substitution
        fluentNotEmpty.mostGeneralUnifier(fluentNotEmpty) shouldBe Substitution.empty()
    }

    @Test
    fun testNotUnifiableFluent() {
        assertFailsWith(
            exceptionClass = NotUnifiableException::class,
            message = "No exception found",
            block = { fluentNotEmpty.mostGeneralUnifier(fluentEmpty) shouldBe Substitution.failed() }
        )
    }
}