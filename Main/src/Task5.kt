import java.math.BigDecimal
import java.math.RoundingMode

class Task5 : Task {

    var n = 0
    var A = Matrix(0,0)
    var v = Matrix(0,0)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal()
            }
        }
        v = Matrix(n, 1)
        for (i in 0 until n) {
            v[i][0] = read.nextBigDecimal()
        }
    }

    var EnA = Matrix(0,0)

    private val minus2 = BigDecimal(-2, Precision.mc)

    override fun execute(): Boolean {
        EnA = A.copy()
//        EnA.print()
        A = v.transpose() * A
//        v.transpose().print()
//        A.print()
        A = v * A
//        A.print()

        A *= minus2
//        A.print()

        A = EnA + A
//        A.print()

        return true
    }

    override fun output() {
        A.print()
    }
}