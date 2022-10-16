package core

import core.resources.TestUtils.fluentEmpty
import core.resources.TestUtils.fluentNotEmpty
import core.resources.TestUtils.name
import core.resources.TestUtils.predicateEmpty
import core.resources.TestUtils.predicateNotEmpty
import core.resources.TestUtils.size
import core.resources.TestUtils.substitution
import core.resources.TestUtils.variableNotEmpty
import domain.BlockWorldDomain.Expressions
import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Predicates
import domain.BlockWorldDomain.Values
import domain.BlockWorldDomain.predicates
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import it.unibo.tuprolog.core.Substitution

class FluentTest : AnnotationSpec() {
    private val variable = Variable.of("different value")
    private val fluent1: Fluent = Fluent.of(
        predicateNotEmpty,
        true,
        List<Value>(size) { variableNotEmpty }

    )
    private val fluent2: Fluent = Fluent.of(
        predicateNotEmpty,
        true,
        List<Value>(size) { variable }
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
        fluentNotEmpty.negate().isNegated shouldBe false
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
    fun testActionObjectWorksAsExpected() {
        val localFluentAtXArm = Fluents.atXArm
        val atA = Fluent.positive(Predicates.at, Values.Y, Values.arm)

        Fluents.atXArm.args.isEmpty() shouldBe false
        Fluents.atXArm.args shouldBe arrayOf(Values.X, Values.arm)
        Fluents.atXArm.instanceOf shouldBeIn predicates
        Fluents.atXArm.isNegated shouldBe false
        Fluents.atXArm.isGround shouldBe false
        Fluents.atXArm.match(Fluents.atXFloor) shouldBe false
        localFluentAtXArm.match(Fluents.atXArm) shouldBe true
        Fluents.atXArm.apply(VariableAssignment.of(Values.X, Values.X)) shouldBe Fluents.atXArm
        Fluents.atXArm.apply(VariableAssignment.of(Values.X, Values.Y)) shouldBe Fluents.atYArm
        Fluents.atXArm.mostGeneralUnifier(Fluents.atXArm) shouldBe Substitution.empty()
        Fluents.atXArm.mostGeneralUnifier(atA) shouldBe VariableAssignment.of(Values.X, Values.Y)
    }

    @Test
    fun testNotUnifiableFluent() {
        shouldThrow<NotUnifiableException> {
            Fluents.atXArm.mostGeneralUnifier(Fluents.onXY) shouldBe Substitution.failed()
            fluentNotEmpty.mostGeneralUnifier(fluentEmpty) shouldBe Substitution.failed()
        }
    }

    @Test
    fun testRefreshWorksAsExpected() {
        fluentNotEmpty shouldBe fluentNotEmpty
        fluentNotEmpty shouldNotBe fluentNotEmpty.refresh()
    }

    @Test fun testExpression() = Expressions.expessionAtArm shouldBe Fluents.atAArm
}
