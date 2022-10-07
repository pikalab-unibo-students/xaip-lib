import domain.BlockWorldDomain.Planners
import domain.GraphDomain.Actions
import domain.GraphDomain.Problems
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class GraphDomainTest : AnnotationSpec() {
    @Test
    fun robotFromLoc1ToLoc2() {
        val plans1 = Planners.stripsPlanner.plan(Problems.robotFromLoc1ToLoc2)
        plans1.toSet().size shouldBe 1
        println(plans1.toSet())
        plans1.toSet().first().operators.first().name shouldBe Actions.move.name
    }

    @Test
    fun inContainerLocation4() {
        val plans = Planners.stripsPlanner.plan(
            Problems.inContainerLocation4
        )
        plans.toSet().size shouldBe 1
        plans.toSet().first().operators.map { it.name }.toList() shouldBe listOf(
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.unload.name
        )
    }

    @Test
    fun robotFromLoc1ToLoc2ContainerFromLocation2ToLocation4() {
        val plans = Planners.stripsPlanner.plan(
            Problems.robotFromLoc1ToLoc2ContainerFromLocation2ToLocation4
        )
        plans.toSet().size shouldBe 1
        plans.toSet().first().operators.map { it.name }.toList() shouldBe listOf(
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.unload.name
        )
    }

    @Test
    fun atRobotAtlocation3InContainer1Location4InContainer2Location7() {
        val plans = Planners.stripsPlanner.plan(
            Problems.robotFromLoc1ToLoc3Container1FromLoc2ToLoc4Container2FromLoc4ToLoc7
        )
        plans.toSet().size shouldBe 1

        plans.toSet().first().operators.map { it.name }.toList() shouldBe listOf(
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.unload.name,
            Actions.move.name,
            Actions.load.name,
            Actions.move.name,
            Actions.unload.name,
            Actions.move.name
        )
    }
}
