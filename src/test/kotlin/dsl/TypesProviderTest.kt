package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import resources.TestUtils.Types
import resources.TestUtils.domainDSL
import resources.TestUtils.types

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class TypesProviderTest : AnnotationSpec() {
    // @Ignore
    @Test
    fun testTypesExists() {
        for (type in domainDSL.types)
            type shouldBeIn types
    }

    @Ignore
    @Test
    fun testTypesNotExists() {
        Types.strings shouldNotBeIn domainDSL.types
    }
}
