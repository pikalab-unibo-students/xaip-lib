import io.kotest.matchers.shouldBe
import org.junit.Test
import resources.Res.objNotEmpty
import resources.Res.objectSetEmpty
import resources.Res.objectSetNotEmpty
import resources.Res.type1

class ObjectSetTest {

    @Test
    fun testEmptyCreation(){
        objectSetEmpty.map.isEmpty() shouldBe true
    }

    @Test
    fun testNotEmpty(){
        objectSetNotEmpty.map.isNotEmpty() shouldBe true
        objectSetNotEmpty.map.size shouldBe 1
        objectSetNotEmpty.map.keys.forEach { it shouldBe type1 }
        objectSetNotEmpty.map.values.forEach { it.toString().replace("[", "").replace("]", "") shouldBe objNotEmpty.toString() }

    }
}