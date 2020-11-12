import org.junit.jupiter.api.Test

internal class Task1Test {

    private val accuracy = 0.000001

    @Test
    fun `if circles are good 1`() {
        val t1 = Task1()
        t1.n = 2
        t1.eps = accuracy
        do {
            t1.A = Matrix.nextMatrix(t1.n, t1.n)
            t1.b = Matrix.nextMatrix(t1.n, 1)
        } while (t1.circlesAreNotInUniteCircle())
        t1.execute()
        assert((t1.x - t1.A * t1.x - t1.b).F() <= t1.eps)
    }

    @Test
    fun `if circles are good 2`() {
        val t1 = Task1()
        t1.n = 4
        t1.eps = accuracy
        do {
            t1.A = Matrix.nextMatrix(t1.n, t1.n)
            t1.b = Matrix.nextMatrix(t1.n, 1)
        } while (t1.circlesAreNotInUniteCircle())
        t1.execute()
        assert((t1.x - t1.A * t1.x - t1.b).F() <= t1.eps)
    }

}