import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils
import resources.TestUtils.States
import resources.TestUtils.Actions

class StateTest : AnnotationSpec() {

    private lateinit var state: State
    private lateinit var applicableAction: Action
    private lateinit var nonApplicableAction: Action
    private lateinit var destinationStates: Set<State>

    @BeforeEach
    fun init() {
        state = TestUtils.States.initial
        applicableAction = TestUtils.Actions.pick
        nonApplicableAction = TestUtils.Actions.stack
        destinationStates = setOf(TestUtils.States.atAArm, TestUtils.States.atBArm, TestUtils.States.atCArm)
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
    fun testStateObjectWorksAsExpected() {
        States.atAArm.fluents.isEmpty() shouldNotBe true
        States.atAArm.fluents.forEach { it.isGround shouldBe true }

        States.atAArm.isApplicable(Actions.stack) shouldBe true
        States.atAArm.isApplicable(Actions.pick) shouldBe false
        val qualcosa= States.atAArm.apply(Actions.pick)
        States.atAArm.apply(Actions.pick).equals(sequenceOf(States.atAArm)
    }
}
