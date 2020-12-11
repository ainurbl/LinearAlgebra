import java.math.BigDecimal

class Task3: Task {

    var n = 0
    var A = Matrix(0, 0)
    var c = BigDecimal.ZERO
    var s = BigDecimal.ZERO
    var i = 0
    var j = 0

    private val eps = BigDecimal(0.000001)


    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = BigDecimal(read.nextDouble())
            }
        }
        i = read.nextInt() - 1
        j = read.nextInt() - 1
        c = read.nextBigDecimal()
        s = read.nextBigDecimal()
        assert((c*c + s*s - BigDecimal.ONE).abs() <= eps)
        assert(i < j && j <= n - 1 && i >= 0)
    }

    override fun execute(): Boolean {
        val v1 = Matrix(1, n)
        val v2 = Matrix(1, n)

        for (k in 0 until n) {
            v1[0][k] = c*A[i][k] + s*A[j][k]
            v2[0][k] = -s*A[i][k] + c*A[j][k]
        }
        for (k in 0 until n) {
            A[i][k] = v1[0][k]
            A[j][k] = v2[0][k]
        }
        return true
    }

    override fun output() {
        A.print()
    }
}