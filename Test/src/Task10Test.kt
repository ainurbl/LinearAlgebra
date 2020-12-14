import Matrix.Companion.nextMatrix
import Matrix.Companion.nextSymMatrix
import Matrix.Companion.nextSymTridiagMatrix
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode


internal class Task10Test {

    private val accuracy = BigDecimal(0.0001).setScale(Precision.scale, RoundingMode.HALF_UP)

    @Test
    fun `simple 1`() {
        val engine = Task10()
        val n = 2
        engine.n = 2
        engine.A = Matrix(n, n)
        engine.A.set(listOf(1, 2, 2, 1))
        val actualA = engine.A.copy()
        engine.eps = accuracy
        engine.execute()
        val eVector = Matrix(n, 1)
        for (i in 0 until n) {
            val lambda = engine.A[i][i]
            for (j in 0 until n) {
                eVector[j][0] = engine.vs[j][i]
            }
            assert((actualA * eVector - eVector * lambda).F() <= accuracy)
        }
    }

    @Test
    fun `strong 1`() {
        val engine = Task10()
        val n = 30
        engine.n = n
        engine.A = nextSymTridiagMatrix(n, n)
        val actualA = engine.A.copy()
        engine.eps = accuracy
        engine.execute()
        val eVector = Matrix(n, 1)
        for (i in 0 until n) {
            val lambda = engine.A[i][i]
            for (j in 0 until n) {
                eVector[j][0] = engine.vs[j][i]
            }
            val tt = (actualA * eVector - eVector * lambda)
//            tt.print()
            assert(tt.F() <= accuracy*BigDecimal(n))
        }
    }

}