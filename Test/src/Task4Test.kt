import Matrix.Companion.nextMatrix
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.MathContext
import kotlin.random.Random.Default.nextDouble

internal class Task4Test {

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

    @Test
    fun `simple 2`() {
        val engine = Task4()
        engine.n = 2
        engine.A = Matrix(2,2)
        engine.A.set(listOf(1,2,3,4))
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.R.isUpperTriangular(accuracy))
        engine.Q.print()
        engine.R.print()
        val QR = engine.Q * engine.R
        engine.A.set(listOf(1,2,3,4))
        assert((QR - engine.A).F() <= accuracy)
    }

    @Test
    fun `simple 3`() {
        val engine = Task4()
        engine.n = 3
        engine.A = Matrix(3,3)
        engine.A.set(listOf(1,2,3,4,5,6,7,8,9))
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.R.isUpperTriangular(accuracy))
        val QR = engine.Q * engine.R
        engine.A.set(listOf(1,2,3,4,5,6,7,8,9))
        assert((QR - engine.A).F() <= accuracy)
    }


    @Test
    fun `strong 1`() {
        val engine = Task4()
        engine.n = 4
        engine.A = nextMatrix(4,4)
        val B = engine.A.copy()
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.R.isUpperTriangular(accuracy))
        val QR = engine.Q * engine.R
        assert((QR - B).F() <= accuracy)
    }


    @Test
    fun `extra strong 2`() {
        val engine = Task4()
        engine.n = 50
        engine.A = nextMatrix(50, 50)
        val B = engine.A.copy()
        engine.execute()
        assert(engine.Q.isOrthogonal(accuracy))
        assert(engine.R.isUpperTriangular(accuracy))
        val QR = engine.Q * engine.R
        assert((QR - B).F() <= accuracy)
    }




}