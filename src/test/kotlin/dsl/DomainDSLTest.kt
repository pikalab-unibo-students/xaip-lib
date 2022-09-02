package dsl // ktlint-disable filename

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils
import resources.TestUtils.Domains
import resources.TestUtils.DomainsDSL

/**
 * Test for DomainDSL cereation.
 */
class DomainDSLTest : AnnotationSpec() {
    @Test
    fun test() {
        DomainsDSL.blockWorldXDomainDSL.name shouldBe Domains.blockWorld.name
        DomainsDSL.blockWorldXDomainDSL.axioms shouldNotBe null
        DomainsDSL.blockWorldXDomainDSL.types shouldBe Domains.blockWorld.types
        DomainsDSL.blockWorldXDomainDSL.actions.first().name shouldBe Domains.blockWorld.actions.first().name
        DomainsDSL.blockWorldXDomainDSL.actions.first().effects.size shouldBe
            Domains.blockWorld.actions.first().effects.size
        DomainsDSL.blockWorldXDomainDSL.actions.first().parameters.size shouldBe
            Domains.blockWorld.actions.first().parameters.size
        DomainsDSL.blockWorldXDomainDSL.actions.first().preconditions.size shouldBe
            Domains.blockWorld.actions.first().preconditions.size
        DomainsDSL.blockWorldXDomainDSL.actions.last().name shouldBe Domains.blockWorld.actions.last().name
        DomainsDSL.blockWorldXDomainDSL.predicates.size shouldBe 4
    }
}
val pick = Action.of(
    name = "pick",
    parameters = mapOf(
        TestUtils.Values.X to TestUtils.Types.blocks
    ),
    preconditions = setOf(
        TestUtils.Fluents.armEmpty,
        TestUtils.Fluents.clearX
    ),
    effects = setOf(
        Effect.of(TestUtils.Fluents.atXArm),
        Effect.negative(TestUtils.Fluents.armEmpty),
        Effect.negative(TestUtils.Fluents.clearX),
        Effect.negative(TestUtils.Fluents.atXFloor)
    )
)

val stack = Action.of(
    name = "stack",
    parameters = mapOf(
        TestUtils.Values.X to TestUtils.Types.blocks,
        TestUtils.Values.Y to TestUtils.Types.locations
    ),
    preconditions = setOf(
        TestUtils.Fluents.atXArm,
        TestUtils.Fluents.clearY
    ),
    effects = setOf(
        Effect.of(TestUtils.Fluents.onXY),
        Effect.of(TestUtils.Fluents.clearX),
        Effect.of(TestUtils.Fluents.armEmpty),
        Effect.negative(TestUtils.Fluents.atXArm),
        Effect.negative(TestUtils.Fluents.clearY)
    )
)

val unStack = Action.of(
    name = "unStack",
    parameters = mapOf(
        TestUtils.Values.X to TestUtils.Types.blocks,
        TestUtils.Values.Y to TestUtils.Types.locations
    ),
    preconditions = setOf(
        TestUtils.Fluents.onXY,
        TestUtils.Fluents.clearX,
        TestUtils.Fluents.armEmpty
    ),
    effects = setOf(
        Effect.negative(TestUtils.Fluents.clearX),
        Effect.negative(TestUtils.Fluents.onXY),
        Effect.negative(TestUtils.Fluents.armEmpty),
        Effect.of(TestUtils.Fluents.atXArm),
        Effect.of(TestUtils.Fluents.clearY)
    )
)
