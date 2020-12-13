import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class Task3Test {

    private val accuracy = BigDecimal(0.000001)
    private val sqrt3 = BigDecimal(3).sqrt(Precision.context)

    @Test
    fun `simple mult 1`() {
        val engine = Task3()
        engine.n = 3
        engine.A = Matrix(engine.n, engine.n)
        engine.A.set(listOf(1,2,3,4,5,6,7,8,9))
        engine.s = BigDecimal(-0.5)
        engine.c = sqrt3/ BigDecimal(2)
        engine.i = 1
        engine.j = 2
        engine.execute()
        val expect = Matrix(3,3)
        expect.set(listOf(1.0, 2.0, 3.0,
                -7.0/2 + 2 *sqrt3.toDouble(), -4 + (5 *sqrt3.toDouble())/2, -9.0/2 + 3 *sqrt3.toDouble(),
                2 + (7 *sqrt3.toDouble())/2, 5.0/2 + 4 *sqrt3.toDouble(), 3 + (9* sqrt3.toDouble())/2))
        assert((engine.A - expect).F() <= accuracy)
    }

    @Test
    fun `simple mult 2`() {
        val engine = Task3()
        engine.n = 3
        engine.A = Matrix(engine.n, engine.n)
        engine.A.set(listOf(1,2,3,4,5,6,7,8,9))
        engine.s = BigDecimal.ONE.negate()
        engine.c = BigDecimal.ZERO
        engine.i = 1
        engine.j = 2
        engine.execute()
        val expect = Matrix(3,3)
        expect.set(listOf(1, 2, 3,
                -7, -8, -9,
                4, 5, 6))
        assert((engine.A - expect).F() <= accuracy)
    }



}