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

    private val minus2 = BigDecimal(-2, Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)

    override fun execute(): Boolean {
        EnA = A.copy()
        A = v.transpose() * A
        A = v * A
        A *= minus2
        A = EnA + A
        return true
    }

    fun executeRight(): Boolean {
        EnA = A.copy()
        A *= v
        A *= v.transpose()
        A *= minus2
        A += EnA
        return true
    }

    override fun output() {
        A.print()
    }
}