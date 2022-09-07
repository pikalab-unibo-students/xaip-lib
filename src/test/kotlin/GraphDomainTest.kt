import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain
import resources.domain.GraphDomain
import resources.domain.GraphDomain.Actions

class GraphDomainTest : AnnotationSpec() {
    @Test
    fun robotFromLoc1ToLoc2() {
        val plans1 = BlockWorldDomain.Planners.dummyPlanner.plan(GraphDomain.Problems.robotFromLoc1ToLoc2)
        plans1.toSet().size shouldBe 1
        println(plans1.toSet())
        plans1.toSet().first().actions.first().name shouldBe Actions.move.name
    }

    @Test
    fun inContainerLocation4() {
        val plans = BlockWorldDomain.Planners.dummyPlanner.plan(
            GraphDomain.Problems.inContainerLocation4
        )
        plans.toSet().size shouldBe 1
        plans.toSet().first().actions.map { it.name }.toList() shouldBe listOf(
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.unload.name
        )
    }

    @Test
    fun robotFromLoc1ToLoc2ContainerFromLocation2ToLocation4() {
        val plans = BlockWorldDomain.Planners.dummyPlanner.plan(
            GraphDomain.Problems.robotFromLoc1ToLoc2ContainerFromLocation2ToLocation4
        )
        plans.toSet().size shouldBe 1
        plans.toSet().first().actions.map { it.name }.toList() shouldBe listOf(
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.unload.name
        )
    }

    @Test
    fun atRobotAtlocation3InContainer1Location4InContainer2Location7() {
        val plans = BlockWorldDomain.Planners.dummyPlanner.plan(
            GraphDomain.Problems.robotFromLoc1ToLoc3Container1FromLoc2ToLoc4Container2FromLoc4ToLoc7
        )
        plans.toSet().size shouldBe 1

        plans.toSet().first().actions.map { it.name }.toList() shouldBe listOf(
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.unload.name,
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.unload.name,
            Actions.move
        )
    }
}
