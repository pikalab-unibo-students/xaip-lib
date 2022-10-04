package explanation

import explanation.impl.SimulatorImpl
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators
class SimulatorTest : AnnotationSpec() {
    val problem = BlockWorldDomain.Problems.armNotEmpty

    private val planPickB = Plan.of(listOf(Operators.pickB))
    private val planPickBstackBApickC = Plan.of(listOf(Operators.pickB, Operators.stackBA, Operators.pickC))
    private val simulationCorrect = SimulatorImpl().simulate(planPickB, problem.initialState)
    private val simulationCorrect2 = SimulatorImpl().simulate(planPickBstackBApickC, problem.initialState)

    @Test
    fun `Test simulation correct`() {
        simulationCorrect.first() shouldBe State.of(
            BlockWorldDomain.Fluents.atBArm,
            BlockWorldDomain.Fluents.atDFloor,
            BlockWorldDomain.Fluents.atCFloor,
            BlockWorldDomain.Fluents.atAFloor,
            BlockWorldDomain.Fluents.clearD,
            BlockWorldDomain.Fluents.clearC,
            BlockWorldDomain.Fluents.clearA
        )
    }

    @Test
    fun `Test simulation correct2`() {
        simulationCorrect2.first() shouldBe State.of(
            BlockWorldDomain.Fluents.onBA,
            BlockWorldDomain.Fluents.atDFloor,
            BlockWorldDomain.Fluents.atCArm,
            BlockWorldDomain.Fluents.atAFloor,
            BlockWorldDomain.Fluents.clearD,
            BlockWorldDomain.Fluents.clearB
        )
    }

}
