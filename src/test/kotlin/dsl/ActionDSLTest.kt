package dsl

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import resources.TestUtils.Actions
import resources.TestUtils.domainDSL

class ActionDSLTest : AnnotationSpec() {
    @Ignore
    @Test
    fun actionDSLworksAsExpected() {
        val actions = domainDSL.actions
        Actions.pick shouldBeIn actions
    }
}
