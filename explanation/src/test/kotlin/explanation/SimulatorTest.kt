package explanation

import explanation.impl.SimulatorImpl
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Operators

class SimulatorTest : AnnotationSpec(){
    val problem = BlockWorldDomain.Problems.armNotEmpty
    val plan = Plan.of(listOf(Operators.pickB))
    val s = SimulatorImpl().simulate(plan.actions, problem.initialState, problem.goal)

    @Test
    fun `Test simulation`() {
        s shouldBe true
    }
}