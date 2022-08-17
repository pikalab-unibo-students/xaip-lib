package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils.DomainDSLs
import resources.TestUtils.Types
import resources.TestUtils.Values

class AxiomDSLTest : AnnotationSpec() {
    private val axioms = DomainDSLs.blockWorldXDomainDSL.axioms

    @Test
    fun axiomDSLworksAsExpected() {
        axioms?.parameters?.keys?.size shouldBe 4
    }

    @Test
    fun axiomDSLparameters() {
        axioms?.parameters?.keys?.first()?.name?.let { it.filter { it.isUpperCase() } } shouldBe
            Values.X.name.filter { it.isUpperCase() }
        axioms?.parameters?.keys?.toList()?.get(1)?.name?.let { it.filter { it.isUpperCase() } } shouldBe
            Values.Y.name.filter { it.isUpperCase() }
        axioms?.parameters?.keys?.toList()?.get(2)?.name?.let { it.filter { it.isUpperCase() } } shouldBe
            Values.W.name.filter { it.isUpperCase() }
        axioms?.parameters?.keys?.toList()?.get(3)?.name?.let { it.filter { it.isUpperCase() } } shouldBe
            Values.Z.name.filter { it.isUpperCase() }

        axioms?.parameters?.values?.first()?.superType shouldBe Types.strings
        axioms?.parameters?.values?.toList()?.get(1)?.superType shouldBe Types.strings

        axioms?.parameters?.values?.toList()?.get(3)?.superType shouldBe Types.anything
        axioms?.parameters?.values?.toList()?.get(2)?.superType shouldBe null
    }
}
