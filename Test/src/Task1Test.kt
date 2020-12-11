import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class Task1Test {

    private val accuracy = BigDecimal(0.000001)

    @Test
    fun `if circles are good 1`() {
        val engine = Task1()
        engine.n = 2
        engine.eps = accuracy
        do {
            engine.A = Matrix.nextMatrix(engine.n, engine.n)
            engine.b = Matrix.nextMatrix(engine.n, 1)
        } while (engine.circlesAreNotInUniteCircle())
        engine.execute()
        assert((engine.x - engine.A * engine.x - engine.b).F() <= engine.eps)
    }

    @Test
    fun `if circles are good 2`() {
        val engine = Task1()
        engine.n = 4
        engine.eps = accuracy
        do {
            engine.A = Matrix.nextMatrix(engine.n, engine.n)
            engine.b = Matrix.nextMatrix(engine.n, 1)
        } while (engine.circlesAreNotInUniteCircle())
        engine.execute()
        assert((engine.x - engine.A * engine.x - engine.b).F() <= engine.eps)
    }

    @Test
    fun `strong 1`() {
        val engine = Task1()
        engine.n = 3
        engine.eps = accuracy
        engine.b = Matrix.nextMatrix(3, 1)
        engine.b.set(listOf(1,2,3))
        engine.A = Matrix(3,3)
        engine.A.set(listOf(1,2,3,4,5,6,7,8,9))
        assert(!engine.execute())
    }

}