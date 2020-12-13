import java.math.BigDecimal

class Task5 : Task {

    var n = 0
    var A = Matrix(0,0)
    var v = Matrix(0,0)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = BigDecimal(read.nextDouble(), Precision.context)
            }
        }
        v = Matrix(n, 1)
        for (i in 0 until n) {
            v[i][0] = read.nextBigDecimal()
        }
    }

    override fun execute(): Boolean {
        val EnA = A.copy()
        A = v.transpose() * A
        A = v * A
        A *= BigDecimal(-2, Precision.context)
        A = EnA + A
        return true
    }

    override fun output() {
        A.print()
    }
}