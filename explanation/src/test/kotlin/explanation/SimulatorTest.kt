package explanation

import explanation.impl.SimulatorImpl
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators

class SimulatorTest : AnnotationSpec() {
    val problem = BlockWorldDomain.Problems.armNotEmpty

    val planPickB = Plan.of(listOf(Operators.pickB))
    val planStackDC = Plan.of(listOf(Operators.stackDC))
    val planPickDStackDC = Plan.of(listOf(Operators.pickD, Operators.stackDC))
    val planPickDStackDCpickA = Plan.of(listOf(Operators.pickD, Operators.stackDC, Operators.pickA))

    val simulationCorrect = SimulatorImpl().simulate(planPickB.actions, problem.initialState, problem.goal)
    val simulationIncorrect = SimulatorImpl().simulate(planStackDC.actions, problem.initialState, problem.goal)
    val simulationIncorrect2 = SimulatorImpl().simulate(planPickDStackDC.actions, problem.initialState, problem.goal)
    val simulationIncorrect3 = SimulatorImpl().simulate(planPickDStackDCpickA.actions, problem.initialState, problem.goal)

    @Test
    fun `Test simulation correct`() {
        simulationCorrect shouldBe true
        // simulationIncorrect3 shouldBe true
    }

    @Test
    fun `Test simulation incorrect`() {
        simulationIncorrect shouldBe false
        simulationIncorrect2 shouldBe false
    }
}
