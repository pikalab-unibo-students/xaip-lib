package dsl

import domain.BlockWorldDomain.Domains
import domain.BlockWorldDomain.DomainsDSL
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

/**
 * Test for DomainDSL cereation.
 */
class DomainDSLTest : AnnotationSpec() {
    @Test
    fun test() {
        DomainsDSL.blockWorld.name shouldBe Domains.blockWorld.name
        DomainsDSL.blockWorld.axioms shouldNotBe null
        DomainsDSL.blockWorld.types shouldBe Domains.blockWorld.types
        DomainsDSL.blockWorld.actions.first().name shouldBe Domains.blockWorld.actions.first().name
        DomainsDSL.blockWorld.actions.first().effects.size shouldBe
            Domains.blockWorld.actions.first().effects.size
        DomainsDSL.blockWorld.actions.first().parameters.size shouldBe
            Domains.blockWorld.actions.first().parameters.size
        DomainsDSL.blockWorld.actions.first().preconditions.size shouldBe
            Domains.blockWorld.actions.first().preconditions.size
        DomainsDSL.blockWorld.actions.last().name shouldBe Domains.blockWorld.actions.toList().last().name
        DomainsDSL.blockWorld.predicates.size shouldBe 4
    }
}
