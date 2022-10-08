import domain.BlockWorldDomain.ObjectSets
import domain.BlockWorldDomain.objects
import domain.BlockWorldDomain.types
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import resources.TestUtils.objNotEmpty
import resources.TestUtils.objectSetEmpty
import resources.TestUtils.objectSetNotEmpty
import resources.TestUtils.type1

class ObjectSetTest : AnnotationSpec() {
    private val regex = Regex("[^A-Za-z0-9()=']")

    @Test
    fun testEmptyCreation() {
        objectSetEmpty.map.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmpty() {
        objectSetNotEmpty.map.isNotEmpty() shouldBe true
        objectSetNotEmpty.map.size shouldBe 1
        objectSetNotEmpty.map.keys.forEach { it shouldBe type1 }
        objectSetNotEmpty.map.values.forEach { it.toString().replace(regex, "") shouldBe objNotEmpty.toString() }
    }

    @Test
    fun testObjectSetObjectWorksAsExpected() {
        ObjectSets.all.map.isEmpty() shouldBe false
        ObjectSets.all.map.keys.forEach { it shouldBeIn types }
        ObjectSets.all.map.values.forEach { it.forEach { it shouldBeIn objects } }
    }
}
