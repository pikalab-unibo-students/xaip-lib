package core

import core.VariableAssignment
import domain.BlockWorldDomain.Values
import domain.BlockWorldDomain.VariableAssignments
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class VariableAssignmentTest : AnnotationSpec() {
    @Test
    fun testVariableAssignmentObjectY2XWorksAsExpected() {
        VariableAssignments.y2x.values shouldBe arrayOf(Values.X)
        VariableAssignments.y2x.size shouldBe 1
        VariableAssignments.y2x.keys shouldBe arrayOf(Values.Y)
        VariableAssignments.y2x.isEmpty() shouldBe false
    }

    @Test
    fun testVariableAssignmentObjectX2FloorWorksAsExpected() {
        VariableAssignments.x2floor.containsKey(Values.X) shouldBe true
        VariableAssignments.x2floor.containsValue(Values.floor) shouldBe true
        VariableAssignments.x2floor[Values.X] shouldBe Values.floor
        VariableAssignments.x2floor.merge(VariableAssignments.y2x) shouldBe VariableAssignment.of(
            Values.Y,
            Values.X
        ).merge(VariableAssignment.of(Values.X, Values.floor))
        VariableAssignments.x2floor.merge(VariableAssignments.x2arm) shouldBe VariableAssignment.empty()
    }
}
