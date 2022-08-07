package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import resources.TestUtils

class TypesProviderTest : AnnotationSpec() {
    @Ignore
    @Test
    fun testTypesExists() {
        val domain = domain {
            // TODO("dovrai importare un object DomainDSL quando esisterà
        }

        TestUtils.Types.blocks shouldBeIn domain.types
    }

    @Ignore
    @Test
    fun testTypesNotExists() {
        val domain = domain {
            // TODO("dovrai importare un object DomainDSL quando esisterà
        }

        TestUtils.Types.strings shouldNotBeIn domain.types
    }
}
