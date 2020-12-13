import Matrix.Companion.nextMatrix
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.MathContext
import kotlin.random.Random.Default.nextDouble

internal class Task5Test {

    private val accuracy = BigDecimal(0.000001)

    @Test
    fun `simple 1`() {
        val engine = Task5()
        val n = 3
        engine.n = n
        engine.A = Matrix(n,n)
        engine.v = Matrix(n, 1)
        engine.v.set(listOf(2,2,8))
        engine.A.set(listOf(1,3,3,7,1,4,8,8,1))
        engine.execute()
        val result = Matrix(n,n)
        result.set(listOf(-319, -285, -85, -313, -287, -84, -1272, -1144, -351))
        assert((result - engine.A).F() <= accuracy)
    }





}