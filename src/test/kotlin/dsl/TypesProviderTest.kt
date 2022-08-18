package dsl

import dsl.provider.TypeProvider
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldNotBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import resources.TestUtils.Domains.blockWorld
import resources.TestUtils.Types
import resources.TestUtils.types

class TypesProviderTest : AnnotationSpec() {
    val typeProvider = TypeProvider.of(setOf(Types.strings, Types.anything))

    @Test
    fun testTypeProviderConstructor() {
        for (type in blockWorld.types)
            type shouldBeIn types
        Type.of("nothing") shouldNotBeIn blockWorld.types
    }

    fun testFindType() {
        typeProvider.findType(Types.strings.name) shouldNotBe null
        typeProvider.findType(Types.anything.name) shouldNotBe null
        typeProvider.findType("nothing") shouldBe null
    }
}
