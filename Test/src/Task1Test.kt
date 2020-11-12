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

}