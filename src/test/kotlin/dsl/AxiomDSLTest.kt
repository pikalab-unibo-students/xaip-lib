package dsl

import BinaryExpression
import Fluent
import UnaryExpression
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import resources.TestUtils
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

    @Test
    fun test() {
        val ctx: BinaryExpression = axioms?.context as BinaryExpression
        val implies: UnaryExpression = axioms.implies as UnaryExpression
        val binaryExpression = BinaryExpression.of(
            Fluent.of(TestUtils.Predicates.on, false, Values.a, Values.b),
            Fluent.of(TestUtils.Predicates.on, false, Values.a, Values.b),
            "or"
        )

        val unaryExpression = UnaryExpression.of(
            Fluent.of(TestUtils.Predicates.on, false, Values.b, Values.c),
            "not"
        )
        (ctx.expression1 as Fluent).name.filter { it.isLowerCase() } shouldBe
            (binaryExpression.expression1 as Fluent).name.filter { it.isLowerCase() }

        (ctx.expression2 as Fluent).name.filter { it.isLowerCase() } shouldBe
                (binaryExpression.expression2 as Fluent).name.filter { it.isLowerCase() }

        (implies.expression as Fluent).name.filter { it.isLowerCase() } shouldBe
                (unaryExpression.expression as Fluent).name.filter { it.isLowerCase() }

    }
}
