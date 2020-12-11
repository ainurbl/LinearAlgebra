import Matrix.Companion.nextMatrix
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.random.Random.Default.nextDouble

internal class Task2Test {

    private val accuracy = BigDecimal(0.000001)

    @Test
    fun `solve equation test1`() {
        val engine = Task2()
        val n = 4
        val L = Matrix(n,n)
        for (i in 0 until n) {
            for (j in 0 until i) {
                L[i][j] = BigDecimal(nextDouble()*5)
            }
            do {
                L[i][i] = BigDecimal(nextDouble()*5)
            } while (L[i][i].abs() < accuracy)
        }
        val b = nextMatrix(n, 1)
        assert((L * engine.solveEquation(L, b) - b).F() <= accuracy)
    }

    @Test
    fun `solve equation test2`() {
        val engine = Task2()
        val n = 500
        val L = Matrix(n,n)
        for (i in 0 until n) {
            for (j in 0 until i) {
                L[i][j] = BigDecimal(nextDouble()*5)
            }
            do {
                L[i][i] = BigDecimal(nextDouble()*5)
            } while (L[i][i].abs() < accuracy)
        }
        val b = nextMatrix(n, 1)
        println((L * engine.solveEquation(L, b) - b).F())
        assert((L * engine.solveEquation(L, b) - b).F() <= accuracy)
    }

    @Test
    fun `simple test 1`() {
        val engine = Task2()
        engine.eps = accuracy
        engine.n = 2
        engine.A = Matrix(engine.n, engine.n)
        engine.b = Matrix(engine.n, 1)

        engine.A.set(listOf(1,0,0,1))
        engine.b.set(listOf(2,-3))

        engine.execute()

        assert((engine.A * engine.x - engine.b).F() <= engine.eps)
    }

    @Test
    fun `simple test 2`() {
        val engine = Task2()
        engine.eps = accuracy
        engine.n = 2
        do {
            engine.A = nextMatrix(engine.n, engine.n)
            engine.b = nextMatrix(engine.n, 1)
        } while (!engine.execute())
        assert((engine.A * engine.x - engine.b).F() <= engine.eps)
    }

}