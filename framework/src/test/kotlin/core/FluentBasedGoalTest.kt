package core

import domain.BlockWorldDomain.Fluents
import domain.BlockWorldDomain.Goals
import domain.BlockWorldDomain.States
import domain.BlockWorldDomain.Values
import domain.BlockWorldDomain.VariableAssignments
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class FluentBasedGoalTest : AnnotationSpec() {
    private val atXarm = FluentBasedGoal.of(Fluents.atXArm)

    @Test
    fun testFluentBasedGoalObjectWorksAsExpected() {
        Goals.pickX shouldBe atXarm
        Goals.pickX.targets.isEmpty() shouldNotBe true
        Goals.pickX.isSatisfiedBy(States.atAArm) shouldBe true
        Goals.pickX.targets.first() shouldBe Fluents.atXArm
        Goals.pickX.targets shouldBe atXarm.targets
        Goals.pickX.targets shouldNotBe atXarm.refresh().targets
    }

    @Test
    fun testRefreshMethodWorksAsExpected() {
        Goals.pickX.targets shouldBe atXarm.targets
        Goals.pickX.targets shouldNotBe atXarm.refresh().targets
    }

    @Test
    fun testApplyMethodWorksAsExpected() {
        Goals.pickX shouldBe atXarm
        Goals.pickX.targets.first().args.first() shouldBe Values.X
        val goalUpdated = Goals.pickX.apply(VariableAssignments.x2a)
        goalUpdated.targets.first().args.first() shouldBe Values.a
    }
}
