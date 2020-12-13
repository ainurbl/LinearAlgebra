import Matrix.Companion.nextMatrix
import Matrix.Companion.nextSymMatrix
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode


internal class Task9Test {

    private val accuracy = BigDecimal(0.000001).setScale(Precision.scale, RoundingMode.HALF_UP)

    @Test
    fun `simple 1`() {
        val engine = Task9()
        val n = 5
        engine.n = n
        engine.A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                engine.A[i][j] = BigDecimal(i + j + 1).setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
        val initA = engine.A.copy()
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.B.isTridiagonal(accuracy))
        assert((initA - engine.Q.transpose() * engine.B * engine.Q).F() <= accuracy)
    }

    @Test
    fun `simple 2`() {
        val engine = Task9()
        val n = 5
        engine.n = n
        engine.A = nextSymMatrix(n, n)
        val initA = engine.A.copy()
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.B.isTridiagonal(accuracy))
        assert((initA - engine.Q.transpose() * engine.B * engine.Q).F() <= accuracy)
    }


    @Test
    fun `simple 3`() {
        val engine = Task9()
        val n = 10
        engine.n = n
        engine.A = nextSymMatrix(n, n)
        val initA = engine.A.copy()
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.B.isTridiagonal(accuracy))
        assert((initA - engine.Q.transpose() * engine.B * engine.Q).F() <= accuracy)
    }

    @Test
    fun `strong 1`() {
        val engine = Task9()
        val n = 100
        engine.n = n
        engine.A = nextSymMatrix(n, n)
        val initA = engine.A.copy()
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.B.isTridiagonal(accuracy))
        assert((initA - engine.Q.transpose() * engine.B * engine.Q).F() <= accuracy)
    }


}