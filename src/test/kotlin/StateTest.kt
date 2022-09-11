import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Actions
import resources.domain.BlockWorldDomain.States

class StateTest : AnnotationSpec() {

    private lateinit var state: State
    private lateinit var applicableAction: Operator
    private lateinit var nonApplicableAction: Operator
    private lateinit var destinationStates: Set<State>

    @BeforeEach
    fun init() {
        state = States.initial
        applicableAction = Operator.of(Actions.pick)
        nonApplicableAction = Operator.of(Actions.stack)
        destinationStates = setOf(States.atAArm, States.atBArm, States.atCArm)
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
        state.isApplicable(applicableAction) shouldBe true
        state.isApplicable(nonApplicableAction) shouldBe false
    }

    @Test
    fun testActionApplicationWorksAsExpected() {
        val actual = state.apply(applicableAction).toSet()
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
            setOf(States.atAArm, States.atCArm, States.atBArm)
    }

    @Test
    fun testStateException() {
        val exception = shouldThrow<IllegalArgumentException> {
            State.of(
                Fluent.of(
                    Predicate.of("error", BlockWorldDomain.Types.anything),
                    false,
                    BlockWorldDomain.Values.X
                )
            )
        }
        exception.message shouldStartWith ("States cannot contain non-ground fluents")
    }
}
