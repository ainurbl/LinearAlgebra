import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Math.abs

internal class MatrixTest {

    private val accuracy = 0.000001

    private fun equals(a: Double, b: Double): Boolean {
        return abs(a - b) < accuracy
    }

    private fun equals(a: Circle, b: Circle): Boolean {
        return abs(a.x - b.x) < accuracy && abs(a.y - b.y) < accuracy && abs(a.r - b.r) < accuracy
    }

    @Test
    fun `matrix unary minus`() {
        val rows = 2
        val cols = 3
        val mat = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                mat[i][j] = i * j + 2 * i + 2 * j + 0.0
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
                a[i][j] = i * j + 2 * i + 2 * j + 0.0
                b[i][j] = 62 * i + 443.0 * j + i * j
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
        a[0][0] = 1.0
        b[0][0] = 3.0
        a[0][1] = 2.0
        b[0][1] = -3.0
        a[1][0] = 3.0
        b[1][0] = 2.0
        a[1][1] = -4.0
        b[1][1] = 1.0
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
                a[i][j] = i * 3 + j + 1.0
                b[j][i] = -i * 3 - j - 1.0
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
        a[0][0] = 1.0
        a[1][1] = 1.0
        a[1][0] = 5.0
        a[0][1] = -2.0
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
        a[0][0] = 4.0
        a[1][0] = 5.0
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
                a[i][j] = i * cols + j + 1.0
            }
        }
        a[rows - 1][cols - 1] = -a[rows - 1][cols - 1]
        val circles = a.getCircles()

        assert(equals(circles[0], Circle(1.0, 0.0, 5.0)))
        assert(equals(circles[1], Circle(5.0, 0.0, 10.0)))
        assert(equals(circles[2], Circle(-9.0, 0.0, 15.0)))

    }
}