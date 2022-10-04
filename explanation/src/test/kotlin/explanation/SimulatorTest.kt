package explanation

import explanation.impl.Answer
import explanation.impl.SimulatorImpl
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators
// TODO(Problema da fixare l'esecuzione di tutti i test della classe fallisce, si può solo eseguir eun metodo per volta)
class SimulatorTest : AnnotationSpec() {
    val problem = BlockWorldDomain.Problems.armNotEmpty

    val planPickB = Plan.of(listOf(Operators.pickB))
    val planStackDC = Plan.of(listOf(Operators.stackDC))
    val planPickDStackDC = Plan.of(listOf(Operators.pickD, Operators.stackDC))
    val planPickDStackDCpickA = Plan.of(listOf(Operators.pickD, Operators.stackDC, Operators.pickA))

    val simulationCorrect = SimulatorImpl().simulate(planPickB, problem.initialState, problem.goal)
    val simulationIncorrect = SimulatorImpl().simulate(planStackDC, problem.initialState, problem.goal)
    val simulationIncorrect2 = SimulatorImpl()
        .simulate(planPickDStackDC, problem.initialState, problem.goal)
    val simulationCorrect2 = SimulatorImpl().simulate(planPickDStackDCpickA, problem.initialState, problem.goal)

    @Test
    fun `Test simulation correct`() {
        simulationCorrect shouldBe true
        simulationCorrect2 shouldBe true
    }

    @Test
    fun `Test simulation incorrect`() {
        simulationIncorrect shouldBe false
        simulationIncorrect2 shouldBe false
    }

    val simulationCorrectA = SimulatorImpl().simulate2(planPickB, problem.initialState, problem.goal)
    val simulationIncorrectA = SimulatorImpl().simulate2(planStackDC, problem.initialState, problem.goal)
    val simulationIncorrectA2 = SimulatorImpl()
        .simulate2(planPickDStackDC, problem.initialState, problem.goal)
    val simulationCorrectA2 = SimulatorImpl().simulate2(planPickDStackDCpickA, problem.initialState, problem.goal)

    @Test
    fun `Test simulation correct A`() {
        simulationCorrectA.planIsAcceptable shouldBe Answer(true).planIsAcceptable
        simulationCorrectA2.planIsAcceptable shouldBe Answer(true).planIsAcceptable
    }

    @Test
    fun `Test simulation incorrect A`() {
        simulationIncorrectA.planIsAcceptable shouldBe Answer(false).planIsAcceptable
        simulationIncorrectA2.planIsAcceptable shouldBe Answer(false).planIsAcceptable

        simulationIncorrectA.operator shouldBe Operators.stackDC
        simulationIncorrectA2.operator shouldBe Operators.stackDC
    }
}
