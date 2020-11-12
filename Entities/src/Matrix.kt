import java.lang.Math.sqrt
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.absoluteValue
import kotlin.random.Random.Default.nextDouble

data class Matrix(val rows: Int, val cols: Int) {
    private val arr: MutableList<MutableList<BigDecimal>> = mutableListOf()

    init {
        for (i in 0 until rows) {
            arr.add(mutableListOf())
            for (j in 0 until cols) {
                arr[i].add(BigDecimal.ZERO)
            }
        }
    }

    fun transpose(): Matrix {
        val returnMatrix = Matrix(cols, rows)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[j][i] = arr[i][j]
            }
        }
        return returnMatrix
    }

    private fun girshgorinRadius(y: Int): BigDecimal {
        var res = BigDecimal.ZERO
        for (i in 0 until cols) {
            res += arr[y][i].abs()
        }
        return res - arr[y][y].abs()
    }

    fun getCircles(): List<Circle> {
        val circles = mutableListOf<Circle>()
        for (y in 0 until rows) {
            circles.add(Circle(arr[y][y], BigDecimal.ZERO, girshgorinRadius(y)))
        }
        return circles
    }

    fun F(): BigDecimal {
        var result = BigDecimal.ZERO
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result += arr[i][j] * arr[i][j]
            }
        }
        return result.sqrt(MathContext(10))
    }

    private fun cuteSeparator() {
        for (i in 0..20) print("=")
        println()
    }

    fun print() {
        cuteSeparator()
        for (i in 0 until rows) {
            if (i > 0) {
                println()
            }
            for (j in 0 until cols) {
                print("${arr[i][j]} ")
            }
        }
        println()
        cuteSeparator()
    }

    operator fun get(y: Int, x: Int) = arr[y][x]

    operator fun get(y: Int) = arr[y]

    operator fun set(y: Int, x: Int, newValue: BigDecimal) {
        arr[y][x] = newValue
    }

    operator fun unaryMinus(): Matrix {
        val returnMatrix = this.copy()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[i][j] = -arr[i][j]
            }
        }
        return returnMatrix
    }

    operator fun plus(otherMatrix: Matrix): Matrix {
        assert(rows == otherMatrix.rows && cols == otherMatrix.cols)
        val returnMatrix = this.copy()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[i][j] = arr[i][j] + otherMatrix[i][j]
            }
        }
        return returnMatrix
    }


    operator fun minus(otherMatrix: Matrix): Matrix {
        assert(rows == otherMatrix.rows && cols == otherMatrix.cols)
        val returnMatrix = this.copy()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[i][j] = arr[i][j] - otherMatrix[i][j]
            }
        }
        return returnMatrix
    }

    operator fun times(otherMatrix: Matrix): Matrix {
        assert(cols == otherMatrix.rows)
        val returnMatrix = Matrix(rows, otherMatrix.cols)
        for (i in 0 until returnMatrix.rows) {
            for (j in 0 until returnMatrix.cols) {
                for (k in 0 until cols) {
                    returnMatrix[i][j] += arr[i][k] * otherMatrix[k][j]
                }
            }
        }
        return returnMatrix
    }

    companion object {
        fun I(n: Int): Matrix {
            val returnMatrix = Matrix(n, n)
            for (i in 0 until n) {
                returnMatrix[i][i] = BigDecimal.ONE
            }
            return returnMatrix
        }

        fun nextMatrix(rows: Int, cols: Int): Matrix {
            val returnMatrix = Matrix(rows, cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    returnMatrix[i][j] = BigDecimal(nextDouble())
                }
            }
            return returnMatrix
        }
    }
}
