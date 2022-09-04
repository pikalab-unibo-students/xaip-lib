package dsl // ktlint-disable filename

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.BlockWorldDomain.Domains
import resources.BlockWorldDomain.DomainsDSL

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
