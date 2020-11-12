import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class MatrixTest {

    private val accuracy = BigDecimal(0.000001)

    private fun equals(a: BigDecimal, b: BigDecimal): Boolean {
        return (a - b).abs() < accuracy
    }

    private fun equals(a: BigDecimal, b: Double): Boolean {
        return (a - BigDecimal(b)).abs() < accuracy
    }

    private fun equals(a: Circle, b: Circle): Boolean {
        return (a.x - b.x).abs() < accuracy && (a.y - b.y).abs() < accuracy && (a.r - b.r).abs() < accuracy
    }

    @Test
    fun `matrix unary minus`() {
        val rows = 2
        val cols = 3
        val mat = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                mat[i][j] = BigDecimal(i * j + 2 * i + 2 * j)
            }
        }
        val minusMat = -mat
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                assert(equals(mat[i][j], -minusMat[i][j]))
            }
        }
    }

    @Test
    fun `matrix plus`() {
        val rows = 3
        val cols = 4
        val a = Matrix(rows, cols)
        val b = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                a[i][j] = BigDecimal(i * j + 2 * i + 2 * j)
                b[i][j] = BigDecimal(62 * i + 443.0 * j + i * j)
            }
        }
        val c = a + b
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                assert(equals(a[i][j] + b[i][j], c[i][j]))
            }
        }
    }

    @Test
    fun `matrix mult 1`() {
        val rows = 2
        val cols = 2
        val a = Matrix(rows, cols)
        val b = Matrix(rows, cols)
        a[0][0] = BigDecimal(1)
        b[0][0] = BigDecimal(3)
        a[0][1] = BigDecimal(2)
        b[0][1] = BigDecimal(-3)
        a[1][0] = BigDecimal(3)
        b[1][0] = BigDecimal(2)
        a[1][1] = BigDecimal(-4)
        b[1][1] = BigDecimal(1)
        val c = a * b
        assert(equals(c[0][0], 7.0))
        assert(equals(c[0][1], -1.0))
        assert(equals(c[1][0], 1.0))
        assert(equals(c[1][1], -13.0))
    }

    @Test
    fun `matrix mult 2`() {
        val rows = 2
        val cols = 3
        val a = Matrix(rows, cols)
        val b = Matrix(cols, rows)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                a[i][j] = BigDecimal(i * 3 + j + 1.0)
                b[j][i] = BigDecimal(-i * 3 - j - 1.0)
            }
        }
        b[1][0] = -b[1][0]
        b[1][1] = -b[1][1]
        val c = a * b
        assert(equals(c[0][0], -6.0))
        assert(equals(c[0][1], -12.0))
        assert(equals(c[1][0], -12.0))
        assert(equals(c[1][1], -27.0))
    }

    @Test
    fun `matrix transpose 1`() {
        val rows = 2
        val cols = 2
        val a = Matrix(rows, cols)
        a[0][0] = BigDecimal(1.0)
        a[1][1] = BigDecimal(1.0)
        a[1][0] = BigDecimal(5.0)
        a[0][1] = BigDecimal(-2.0)
        val b = a.transpose()
        assert(equals(b[0][0], 1.0))
        assert(equals(b[0][1], 5.0))
        assert(equals(b[1][0], -2.0))
        assert(equals(b[1][1], 1.0))
    }

    @Test
    fun `matrix transpose 2`() {
        val rows = 2
        val cols = 1
        val a = Matrix(rows, cols)
        a[0][0] = BigDecimal(4.0)
        a[1][0] = BigDecimal(5.0)
        val b = a.transpose()
        assert(equals(b[0][0], a[0][0]))
        assert(equals(b[0][1], a[1][0]))
    }

    @Test
    fun `matrix girshgorin circles`() {
        val rows = 3
        val cols = 3
        val a = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                a[i][j] = BigDecimal(i * cols + j + 1.0)
            }
        }
        a[rows - 1][cols - 1] = -a[rows - 1][cols - 1]
        val circles = a.getCircles()

        assert(equals(circles[0], Circle( BigDecimal(1), BigDecimal.ZERO, BigDecimal(5))))
        assert(equals(circles[1], Circle( BigDecimal(5),  BigDecimal.ZERO,  BigDecimal(10))))
        assert(equals(circles[2], Circle( BigDecimal(-9), BigDecimal.ZERO,  BigDecimal(15))))

    }
}