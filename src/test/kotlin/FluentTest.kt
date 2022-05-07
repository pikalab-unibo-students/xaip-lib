import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Test
import resources.res.nameGC
import resources.res.predicate

class FluentTest {

    private val fluentEmpty = mockkClass(Fluent::class){
        every{ name } returns ""
        every { args } returns emptyList()
        every{ instanceOf} returns predicate
        every{ isNegated} returns false
    }
    private val fluentNotEmpty = mockkClass(Fluent::class){
        every{ name } returns nameGC
        every {args } returns mockk(relaxed =true)
        every{instanceOf} returns mockk( relaxed = true)
        every{isNegated} returns true
    }

    @Test
    fun testEmptyCreation() {
        fluentEmpty.name shouldBe ""
        fluentEmpty.args.isEmpty() shouldBe true
        fluentEmpty.instanceOf shouldBe predicate
        fluentEmpty.isNegated shouldBe false
    }
    @Test
    fun testNotEmptyCreation() {
        fluentNotEmpty.name shouldBe nameGC
        fluentNotEmpty.args.isEmpty() shouldBe false
        fluentNotEmpty.instanceOf shouldNotBe predicate
        fluentNotEmpty.isNegated shouldBe true
    }
}