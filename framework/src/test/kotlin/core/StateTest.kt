package core

import domain.BlockWorldDomain.Actions
import domain.BlockWorldDomain.States
import domain.BlockWorldDomain.Types
import domain.BlockWorldDomain.Values
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

class StateTest : AnnotationSpec() {

    private lateinit var state: State
    private lateinit var applicableOperator: Operator
    private lateinit var nonApplicableOperator: Operator
    private lateinit var destinationStates: Set<State>

    @BeforeEach
    fun init() {
        state = States.initial
        applicableOperator = Operator.of(Actions.pick)
        nonApplicableOperator = Operator.of(Actions.stack)
        destinationStates = setOf(States.atAArm, States.atBArm, States.atCArm, States.atDArm)
    }

    @Test
    fun testEmptyCreation() {
        state.fluents.isEmpty() shouldBe false
    }

    @Test
    fun testNotEmptyCreation() {
        state.fluents.isEmpty() shouldBe false
        state.fluents.forEach { it.isGround shouldBe true }
    }

    @Test
    fun testIsApplicableWorkAsExpected() {
        state.isApplicable(applicableOperator) shouldBe true
        state.isApplicable(nonApplicableOperator) shouldBe false
    }

    @Test
    fun testActionApplicationWorksAsExpected() {
        val actual = state.apply(applicableOperator).toSet()
        actual shouldBe destinationStates
        actual.first().fluents.forEach { it shouldBeIn destinationStates.first().fluents }
    }

    @Test
    fun testStateObjectAtAArmWorksAsExpected() {
        States.atAArm.fluents.isEmpty() shouldNotBe true
        States.atAArm.fluents.forEach { it.isGround shouldBe true }
    }

    @Test
    fun testStateObjectAtAArmIsApplicableWorksAsExpected() {
        States.atAArm.isApplicable(Actions.stack) shouldBe true
        States.atAArm.isApplicable(Actions.pick) shouldBe false
    }

    @Test
    fun testStateObjectAtAArmApplyWorksAsExpected() {
        States.initial.apply(Operator.of(Actions.pick)).toSet() shouldBe
            setOf(States.atAArm, States.atCArm, States.atBArm, States.atDArm)
    }

    @Test
    fun testStateException() {
        val exception = shouldThrow<IllegalArgumentException> {
            State.of(
                Fluent.of(
                    Predicate.of("error", Types.anything),
                    false,
                    Values.X
                )
            )
        }
        exception.message shouldStartWith ("States cannot contain non-ground fluents")
    }
}
