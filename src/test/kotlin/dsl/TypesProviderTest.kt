package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import resources.TestUtils.Types
import resources.TestUtils.domainDSL

class TypesProviderTest : AnnotationSpec() {
    @Ignore
    @Test
    fun testTypesExists() {
        Types.blocks shouldBeIn domainDSL.types
    }

    @Ignore
    @Test
    fun testTypesNotExists() {
        Types.strings shouldNotBeIn domainDSL.types
    }
}
