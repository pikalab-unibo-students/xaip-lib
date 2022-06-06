import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils
import resources.TestUtils.substitution

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
    @Ignore
    fun testApplyWorksAsExpected() {
        state.apply(substitution) shouldBe state
        TODO(
            "effettivamente non serve applicare una sostituzione allo stato se è ground... direi che possiamo " +
                    "togliere Applicable dai supertipi di State, e quindi questo test"
        )
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
}
