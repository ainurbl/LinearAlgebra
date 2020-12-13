import java.lang.ArithmeticException
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random.Default.nextDouble

data class Complex(val re: BigDecimal, val im: BigDecimal) {
    fun abs(): BigDecimal {
        return (re * re + im * im).sqrt(Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
    }

    operator fun times(other: Complex): Complex {
        return Complex(re * other.re - im * other.im, re * other.im + other.re * im)
    }

    operator fun times(other: BigDecimal): Complex {
        return Complex(re * other, other * im)
    }

    operator fun plus(other: Complex): Complex {
        return Complex(re + other.re, im + other.im)
    }

    operator fun minus(other: Complex): Complex {
        return Complex(re - other.re, im - other.im)
    }

    operator fun unaryMinus(): Complex {
        return Complex(-re, -im)
    }

    operator fun div(complex: Complex): Complex {
        if (complex.abs() <= BigDecimal(0.000000001)) throw ArithmeticException("Dividing by 0")
        val cc = this * Complex(complex.re, -complex.im)
        val cd = complex.abs()
        val cdcd = cd * cd
        return Complex(cc.re / (cdcd), cc.im / (cdcd))

    }
}

data class CMatrix(val rows: Int, val cols: Int) {
    private val arr: MutableList<MutableList<Complex>> = mutableListOf()

    init {
        for (i in 0 until rows) {
            arr.add(mutableListOf())
            for (j in 0 until cols) {
                arr[i].add(Complex(BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP), BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)))
            }
        }
    }

    fun copy(): CMatrix {
        val ret = CMatrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                ret[i][j] = Complex(arr[i][j].re.setScale(Precision.scale, RoundingMode.HALF_UP), arr[i][j].im.setScale(Precision.scale, RoundingMode.HALF_UP))
            }
        }
        return ret
    }

    fun set(list: List<Complex>) {
        assert(list.size == rows * cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                arr[i][j] = list[j + i * cols]
            }
        }
    }

    @JvmName("set1")
    fun set(list: List<Int>) {
        assert(list.size == rows * cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                arr[i][j] = Complex(BigDecimal(list[j + i * cols]), BigDecimal.ZERO)
            }
        }
    }

    fun isZero(i: Int, j: Int, eps: BigDecimal): Boolean {
        return (arr[i][j].re.setScale(Precision.scale, RoundingMode.HALF_UP) +
                arr[i][j].im.setScale(Precision.scale, RoundingMode.HALF_UP)).abs() <= eps
    }

    fun isOrthogonal(eps: BigDecimal): Boolean {
        if (rows != cols) return false
//        (this * transpose() - I(rows)).print()
        return (this * transpose() - I(rows)).F() <= eps
    }

    fun isUpperTriangular(eps: BigDecimal): Boolean {
        for (i in 0 until rows) {
            for (j in 0 until i) {
                if (!isZero(i, j, eps)) return false
            }
        }
        return true
    }

    fun transpose(): CMatrix {
        val returnMatrix = CMatrix(cols, rows)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[j][i] = arr[i][j]
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
            circles.add(Circle(arr[y][y].re, arr[y][y].im, girshgorinRadius(y)))
        }
        return circles
    }

    fun F(): BigDecimal {
        var result = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result += arr[i][j].abs() * arr[i][j].abs()
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

    operator fun get(y: Int, x: Int) = arr[y][x]

    operator fun get(y: Int) = arr[y]

    operator fun set(y: Int, x: Int, newValue: Complex) {
        arr[y][x] = newValue
    }

    operator fun unaryMinus(): CMatrix {
        val returnMatrix = this.copy()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[i][j] = -arr[i][j]
            }
        }
        return returnMatrix
    }

    operator fun plus(otherMatrix: CMatrix): CMatrix {
        assert(rows == otherMatrix.rows && cols == otherMatrix.cols)
        val returnMatrix = this.copy()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[i][j] = arr[i][j] + otherMatrix[i][j]
            }
        }
        return returnMatrix
    }


    operator fun minus(otherMatrix: CMatrix): CMatrix {
        assert(rows == otherMatrix.rows && cols == otherMatrix.cols)
        val returnMatrix = this.copy()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                returnMatrix[i][j] = arr[i][j] - otherMatrix[i][j]
            }
        }
        return returnMatrix
    }

    operator fun times(otherMatrix: CMatrix): CMatrix {
        assert(cols == otherMatrix.rows)
        val returnMatrix = CMatrix(rows, otherMatrix.cols)
        for (i in 0 until returnMatrix.rows) {
            for (j in 0 until returnMatrix.cols) {
                for (k in 0 until cols) {
                    returnMatrix[i][j] = (returnMatrix[i][j] + arr[i][k] * otherMatrix[k][j])

                }
            }
        }
        return returnMatrix
    }

    operator fun times(bigDecimal: BigDecimal): CMatrix {
        val returnMatrix = CMatrix(rows, cols)
        for (i in 0 until returnMatrix.rows) {
            for (j in 0 until returnMatrix.cols) {
                returnMatrix[i][j] = arr[i][j] * bigDecimal
            }
        }
        return returnMatrix
    }

    operator fun times(bigDecimal: Complex): CMatrix {
        val returnMatrix = CMatrix(rows, cols)
        for (i in 0 until returnMatrix.rows) {
            for (j in 0 until returnMatrix.cols) {
                returnMatrix[i][j] = arr[i][j] * bigDecimal
            }
        }
        return returnMatrix
    }

    companion object {
        fun I(n: Int): CMatrix {
            val returnMatrix = CMatrix(n, n)
            for (i in 0 until n) {
                returnMatrix[i][i] = Complex(BigDecimal.ONE.setScale(Precision.scale, RoundingMode.HALF_UP),
                        BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP))
            }
            return returnMatrix
        }

        fun nextMatrix(rows: Int, cols: Int): CMatrix {
            val returnMatrix = CMatrix(rows, cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    returnMatrix[i][j] = Complex(BigDecimal(nextDouble()).setScale(Precision.scale, RoundingMode.HALF_UP), BigDecimal(nextDouble()).setScale(Precision.scale, RoundingMode.HALF_UP))
                }
            }
            return returnMatrix
        }
    }
}
