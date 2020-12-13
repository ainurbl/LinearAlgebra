import Matrix.Companion.nextMatrix
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.MathContext
import kotlin.random.Random.Default.nextDouble

internal class Task5Test {

    private val accuracy = BigDecimal(0.000001)

    @Test
    fun `simple 1`() {
        val engine = Task4()
        engine.n = 2
        engine.A = Matrix(2,2)
        engine.A.set(listOf(0,1,1,0))
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.R.isUpperTriangular(accuracy))

        val QR = engine.Q * engine.R
        engine.A.set(listOf(0,1,1,0))

        assert((QR - engine.A).F() <= accuracy)
    }





}