import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random.Default.nextDouble

data class Matrix(val rows: Int, val cols: Int) {
    private val arr: MutableList<MutableList<BigDecimal>> = mutableListOf()

    init {
        for (i in 0 until rows) {
            arr.add(mutableListOf())
            for (j in 0 until cols) {
                arr[i].add(BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP))
            }
        }
    }

    fun copy(): Matrix {
        val ret = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                ret[i][j] = arr[i][j].setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
        return ret
    }

    fun set(list: List<BigDecimal>) {
        assert(list.size == rows * cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                arr[i][j] = list[j + i * cols].setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
    }

    @JvmName("set1")
    fun set(list: List<Double>) {
        assert(list.size == rows * cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                arr[i][j] = BigDecimal(list[j + i * cols]).setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
    }

    @JvmName("set2")
    fun set(list: List<Long>) {
        assert(list.size == rows * cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                arr[i][j] = BigDecimal(list[j + i * cols]).setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
    }

    fun isZero(i: Int, j: Int, eps: BigDecimal): Boolean {
        return arr[i][j].abs().setScale(Precision.scale, RoundingMode.HALF_UP) <= eps
    }

    fun isTridiagonal(eps: BigDecimal): Boolean {
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (Math.abs(i-j) >= 2) {
                    if(arr[i][j].abs() > eps)return false
                }
            }
        }
        return true
    }

    fun isOrthogonal(eps: BigDecimal): Boolean {
        if (rows != cols) return false
//        (this * transpose() - I(rows)).print()
        return (this * transpose() - I(rows)).F() <= eps
    }

    fun isSymmetrical(eps: BigDecimal): Boolean {
        if(rows != cols) return false
        return (this - transpose()).F() <= eps
    }

    fun isUpperTriangular(eps: BigDecimal): Boolean {
        for (i in 0 until rows) {
            for (j in 0 until i) {
                if (!isZero(i, j, eps)) return false
            }
        }
        return true
    }

    fun transpose(): Matrix {
        val returnMatrix = Matrix(cols, rows)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[j][i] = arr[i][j].setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
        return returnMatrix
    }

    private fun girshgorinRadius(y: Int): BigDecimal {
        var res = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)
        for (i in 0 until cols) {
            res += arr[y][i].abs()
        }
        return res - arr[y][y].abs()
    }

    fun getCircles(): List<Circle> {
        val circles = mutableListOf<Circle>()
        for (y in 0 until rows) {
            circles.add(Circle(arr[y][y].setScale(Precision.scale, RoundingMode.HALF_UP), BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP), girshgorinRadius(y).setScale(Precision.scale, RoundingMode.HALF_UP)))
        }
        return circles
    }

    fun allCirclesAreLessThan(eps: BigDecimal): Boolean {
        return getCircles().all { circle ->
            circle.r <= eps
        }
    }

    fun F(): BigDecimal {
        var result = BigDecimal.ZERO .setScale(Precision.scale, RoundingMode.HALF_UP)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result += arr[i][j] * arr[i][j]
            }
        }
        return result.sqrt(Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
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

    operator fun get(y: Int, x: Int) = arr[y][x].setScale(Precision.scale, RoundingMode.HALF_UP)

    operator fun get(y: Int) = arr[y]

    operator fun set(y: Int, x: Int, newValue: BigDecimal) {
        arr[y][x] = newValue.setScale(Precision.scale, RoundingMode.HALF_UP)
    }

    operator fun unaryMinus(): Matrix {
        val returnMatrix = this.copy()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[i][j] = -arr[i][j].setScale(Precision.scale, RoundingMode.HALF_UP)
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
                returnMatrix[i][j].setScale(Precision.scale, RoundingMode.HALF_UP)
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
                returnMatrix[i][j].setScale(Precision.scale, RoundingMode.HALF_UP)
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
                    returnMatrix[i][j] = (returnMatrix[i][j]  + arr[i][k] * otherMatrix[k][j]).setScale(Precision.scale, RoundingMode.HALF_UP)

                }
            }
        }
        return returnMatrix
    }

    operator fun times(bigDecimal: BigDecimal): Matrix {
        val returnMatrix = Matrix(rows, cols)
        for (i in 0 until returnMatrix.rows) {
            for (j in 0 until returnMatrix.cols) {
                returnMatrix[i][j] = (arr[i][j] * bigDecimal).setScale(Precision.scale, RoundingMode.HALF_UP)

            }
        }
        return returnMatrix
    }

    companion object {
        fun I(n: Int): Matrix {
            val returnMatrix = Matrix(n, n)
            for (i in 0 until n) {
                returnMatrix[i][i] = BigDecimal.ONE .setScale(Precision.scale, RoundingMode.HALF_UP)
            }
            return returnMatrix
        }

        fun nextMatrix(rows: Int, cols: Int): Matrix {
            val returnMatrix = Matrix(rows, cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    returnMatrix[i][j] = BigDecimal(nextDouble()).setScale(Precision.scale, RoundingMode.HALF_UP)
                }
            }
            return returnMatrix
        }

        fun nextSymMatrix(rows: Int, cols: Int): Matrix {
            val returnMatrix = Matrix(rows, cols)
            for (i in 0 until rows) {
                for (j in i until cols) {
                    returnMatrix[i][j] = BigDecimal(nextDouble()).setScale(Precision.scale, RoundingMode.HALF_UP)
                }
            }
            for(i in 0 until  rows) {
                for (j in 0 until i) {
                    returnMatrix[i][j] = returnMatrix[j][i]
                }
            }
            return returnMatrix
        }

    }
}
