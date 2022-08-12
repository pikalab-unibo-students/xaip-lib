package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import resources.TestUtils.domainDSL
import resources.TestUtils.types

class TypesProviderTest : AnnotationSpec() {

    @Test
    fun testTypesExists() {
        for (type in domainDSL.types)
            type shouldBeIn types
    }

    @Test
    fun testTypesNotExists() {
        Type.of("nothing") shouldNotBeIn domainDSL.types
    }
}
