
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.zip.ZipEntry
import kotlin.math.sqrt

internal class Task7Test {

    private val accuracy = BigDecimal(0.000001)

    @Test
    fun `simple 1`() {
        val engine = Task7()
        val n = 1
        engine.n = n
        engine.A = CMatrix(n,n)
        engine.x = CMatrix(n, 1)
        engine.A.set(listOf(Complex(BigDecimal.ONE, BigDecimal.ONE)))
        engine.x.set(listOf(Complex(BigDecimal(1), BigDecimal.ZERO)))
        engine.eps = accuracy
        assert(engine.execute())
        assert((engine.A * engine.x - engine.x * engine.lambda).F() <= accuracy)
//        println(engine.execute())
//        engine.x.print()
//        println(engine.lambda)
//        (engine.x * engine.lambda).print()
//        engine.A.print()
    }
    @Test
    fun `simple 2`() {
        val engine = Task7()
        val n = 2
        engine.n = n
        engine.A = CMatrix(n,n)
        engine.x = CMatrix(n,1)
        engine.eps = accuracy
        engine.A.set(listOf(1, 0, 0, 1))
        engine.x.set(listOf(0,1))
        assert(engine.execute())
        assert((engine.A * engine.x - engine.x * engine.lambda).F() <= accuracy)
    }

    @Test
    fun `simple 3`() {
        val engine = Task7()
        val n = 2
        engine.n = n
        engine.A = CMatrix(n,n)
        engine.eps = accuracy
        engine.x = CMatrix(n,1)
        engine.A.set(listOf(-1, 2, 0, 3))
        engine.x.set(listOf(0,1))
        assert(engine.execute())
        assert((engine.A * engine.x - engine.x * engine.lambda).F() <= accuracy)
        assert((engine.lambda - Complex(BigDecimal(3), BigDecimal.ZERO)).abs() <= accuracy)
    }


    @Test
    fun `simple 4`() {
        val engine = Task7()
        val n = 2
        engine.n = n
        engine.A = CMatrix(n,n)
        engine.eps = accuracy
        engine.x = CMatrix(n,1)
        engine.A.set(listOf(4, 1, 2, -1))
        engine.x.set(listOf(0,1))
        assert(engine.execute())
        assert((engine.A * engine.x - engine.x * engine.lambda).F() <= accuracy)
        assert((engine.lambda - Complex(BigDecimal(4.3722813232690143299253057341094646591101322289913961838499387352), BigDecimal.ZERO)).abs() <= accuracy)
    }


}