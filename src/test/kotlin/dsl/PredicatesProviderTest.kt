package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import resources.TestUtils

class PredicatesProviderTest : AnnotationSpec() {
    @Ignore
    @Test
    fun testPredicateExists() {
        val domain = domain {
            // TODO("dovrai importare un object DomainDSL quando esisterà
        }

        TestUtils.Predicates.on shouldBeIn domain.predicates
    }

    @Ignore
    @Test
    fun testPredicateNotExists() {
        val domain = domain {
            // TODO("dovrai importare un object DomainDSL quando esisterà
        }

        TestUtils.Predicates.at shouldNotBeIn domain.predicates
    }
}
