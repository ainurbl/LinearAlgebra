import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class Task2 : Task { // Lx_{i+1} = -Ux_{i} + b, L + U = A
    var n = 0
    var A = Matrix(0, 0)
    var b = Matrix(0, 0)
    var eps = BigDecimal.ZERO
    var x = Matrix(0, 0)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        b = Matrix(n, 1)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal()
            }
        }
        for (i in 0 until n) {
            b[i][0] = read.nextBigDecimal()
        }
        eps = read.nextBigDecimal()
    }

    private var cantReach = false

    override fun execute(): Boolean {
        val L = Matrix(n, n)
        val minusU = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (j > i) {
                    minusU[i][j] = -A[i][j]
                } else {
                    L[i][j] = A[i][j]
                }
            }
        }
        x = Matrix(n, 1)
        var bad = true
        for (i in 0 until n) {
            if (A[i][i].abs() > eps) bad = false
        }
        if (bad) {
            cantReach = true
            return false
        }
        var badCounter = 0
        do {
            val newX = solveEquation(L, minusU * x + b, Precision.mc)
            if (newX.F() >= x.F() + BigDecimal.ONE .setScale(Precision.scale, RoundingMode.HALF_UP)) {
                badCounter++
            } else {
                badCounter = 0
            }
            if (badCounter >= 20) {
                cantReach = true
                return false
            }
            x = newX
        } while ((A * x - b).F() >= eps)
        return true
    }

    fun solveEquation(L: Matrix, b: Matrix, context: MathContext): Matrix {
        val n = L.rows
        val returnMatrix = Matrix(n, 1)
        returnMatrix[0][0] = b[0][0].divide(L[0][0], context)
        for (i in 1 until n) {
            var tmp = b[i][0]
            for (j in 0 until i) {
                tmp -= L[i][j] * returnMatrix[j][0]
            }
            returnMatrix[i][0] = tmp.divide(L[i][i], context)
        }

        return returnMatrix
    }

    override fun output() {
        if (cantReach) {
            println(0)
            println("Can not reach solution")
        } else {
            x.print()
        }
    }


}