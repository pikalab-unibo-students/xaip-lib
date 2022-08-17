package dsl // ktlint-disable filename

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.DomainDSLs
import resources.TestUtils.Domains

/**
 * Test for DomainDSL cereation.
 */
class DomainDSLTest : AnnotationSpec() {
    @Test
    fun test() {
        DomainDSLs.blockWorldXDomainDSL.name shouldBe Domains.blockWorld.name
        DomainDSLs.blockWorldXDomainDSL.axioms shouldNotBe null
        DomainDSLs.blockWorldXDomainDSL.types shouldBe Domains.blockWorld.types
        DomainDSLs.blockWorldXDomainDSL.actions.first().name shouldBe Domains.blockWorld.actions.first().name
        DomainDSLs.blockWorldXDomainDSL.actions.first().effects.size shouldBe
            Domains.blockWorld.actions.first().effects.size
        DomainDSLs.blockWorldXDomainDSL.actions.first().parameters.size shouldBe
            Domains.blockWorld.actions.first().parameters.size
        DomainDSLs.blockWorldXDomainDSL.actions.first().preconditions.size shouldBe
            Domains.blockWorld.actions.first().preconditions.size
        DomainDSLs.blockWorldXDomainDSL.actions.last().name shouldBe Domains.blockWorld.actions.last().name
        DomainDSLs.blockWorldXDomainDSL.predicates.size shouldBe 4
    }
}
