package explanation

import explanation.impl.SimulatorImpl
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators

class SimulatorTest : AnnotationSpec() {
    val problem = BlockWorldDomain.Problems.armNotEmpty
    val plan = Plan.of(listOf(Operators.pickB))
    val plan2 = Plan.of(listOf(Operators.stackDC))
    val s = SimulatorImpl().simulate(plan.actions, problem.initialState, problem.goal)

    val s2 = SimulatorImpl().simulate(plan2.actions, problem.initialState, problem.goal)

    @Test
    fun `Test simulation`() {
        s shouldBe true
        s2 shouldBe false

    }
}
