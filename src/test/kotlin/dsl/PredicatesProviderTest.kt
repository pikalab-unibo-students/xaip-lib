package dsl

import dsl.provider.PredicateProvider
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.domain.BlockWorldDomain
import resources.domain.BlockWorldDomain.Predicates

class PredicatesProviderTest : AnnotationSpec() {
    private val predicateProvider =
        PredicateProvider.of(setOf(Predicates.on, Predicates.armEmpty, Predicates.at, Predicates.clear))
    private val predicateProviderFramework = PredicateProvider.of(BlockWorldDomain.Domains.blockWorld)
    private val predicateProviderDSL = PredicateProvider.of(BlockWorldDomain.DomainsDSL.blockWorldXDomainDSL)

    @Test
    fun testPredicateProviderConstructor() {
        predicateProviderFramework.findPredicate(Predicates.on.name, Predicates.on.arguments.size) shouldBe
            Predicates.on
        predicateProviderDSL.findPredicate(Predicates.on.name, Predicates.on.arguments.size) shouldBe
            Predicates.on
        predicateProvider.findPredicate(Predicates.on.name, Predicates.on.arguments.size) shouldBe
            Predicates.on
    }

    @Test
    fun testPredicateExists() {
        predicateProvider.findPredicate(Predicates.at.name, Predicates.at.arguments.size) shouldBe
            Predicates.at
        predicateProvider.findPredicate(Predicates.clear.name, Predicates.clear.arguments.size) shouldBe
            Predicates.clear
        predicateProvider.findPredicate(Predicates.on.name, Predicates.on.arguments.size) shouldBe
            Predicates.on
        predicateProvider.findPredicate(Predicates.armEmpty.name, Predicates.armEmpty.arguments.size) shouldBe
            Predicates.armEmpty
    }

    @Test
    fun testPredicateNotExists() {
        predicateProvider.findPredicate(Predicates.on.name, Predicates.on.arguments.size - 1) shouldBe null
        predicateProvider.findPredicate(Predicates.armEmpty.name, Predicates.armEmpty.arguments.size + 1) shouldBe null
        predicateProvider.findPredicate("nothing", 1) shouldBe null
    }
}
