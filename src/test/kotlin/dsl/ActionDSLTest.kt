package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import resources.TestUtils.Actions
import resources.TestUtils.domainDSL

// TODO imho thesing single dsl classes in this phase just wastes your time. I would start by test the DSL as a whole
class ActionDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun actionDSLworksAsExpected() {
        val actions = domainDSL.actions
        Actions.pick shouldBeIn actions
    }
}
