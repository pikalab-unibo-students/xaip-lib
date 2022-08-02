import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Actions
import resources.TestUtils.States

class StateTest : AnnotationSpec() {

    private lateinit var state: State
    private lateinit var applicableAction: Action
    private lateinit var nonApplicableAction: Action
    private lateinit var destinationStates: Set<State>

    @BeforeEach
    fun init() {
        state = States.initial
        applicableAction = Actions.pick
        nonApplicableAction = Actions.stack
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
    fun testActionApplication() {
        val actual = state.apply(applicableAction).toSet()
        actual shouldBe destinationStates
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
        States.initial.apply(Actions.pick).toSet() shouldBe (setOf(States.atAArm, States.atCArm, States.atBArm))
    }
}
