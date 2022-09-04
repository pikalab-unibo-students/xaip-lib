import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain
import resources.domain.GridDomain
import resources.domain.GridDomain.Actions

class GridDomainTest : AnnotationSpec() {
    @Test
    fun robotFromLoc1ToLoc2() {
        val plans1 = BlockWorldDomain.Planners.dummyPlanner.plan(GridDomain.Problems.robotFromLoc1ToLoc2)
        plans1.toSet().size shouldBe 2
        println(plans1.toSet())
        plans1.toSet().first().actions.first().name shouldBe Actions.move.name
    }

    @Ignore
    @Test
    fun inContainerLocation4() {
        val plans = BlockWorldDomain.Planners.dummyPlanner.plan(
            GridDomain.Problems.inContainerLocation4
        )
        plans.toSet().size shouldBe 1
        plans.toSet().first().actions.map { it.name }.toList() shouldBe listOf(
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.move.name,
            Actions.unload.name
        )
    }

    @Ignore
    @Test
    fun robotFromLoc1ToLoc2ContainerFromLocation2ToLocation4() {
        val plans = BlockWorldDomain.Planners.dummyPlanner.plan(
            GridDomain.Problems.robotFromLoc1ToLoc2ContainerFromLocation2ToLocation4
        )
        plans.toSet().size shouldBe 1
        plans.toSet().first().actions.map { it.name }.toList() shouldBe listOf(
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.move.name,
            Actions.unload.name,
            Actions.move.name
        )
    }
}
