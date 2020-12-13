import java.math.BigDecimal

class Task3 : Task {

    var n = 0
    var A = Matrix(0, 0)
    var c = BigDecimal.ZERO
    var s = BigDecimal.ZERO
    var i = 0
    var j = 0

    private val eps = BigDecimal(0.000001)

    fun getFromOther(task: Task3) {
        n = task.n
        c = task.c
        s = task.s
        i = task.i
        j = task.j
    }

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = BigDecimal(read.nextDouble(), Precision.context)
            }
        }
        i = read.nextInt() - 1
        j = read.nextInt() - 1
        c = read.nextBigDecimal()
        s = read.nextBigDecimal()
        assert((c * c + s * s - BigDecimal(1, Precision.context)).abs() <= eps)
        assert(i < j && j <= n - 1 && i >= 0)
    }

    fun erase(x: Int, i1: Int, i2: Int) {
        i = i1
        j = i2
        val denom = (A[i1][x].pow(2) + A[i2][x].pow(2)).sqrt(Precision.context)
        c = A[i1][x].divide(denom, Precision.context)
        s = A[i2][x].divide(denom, Precision.context)
    }


    override fun execute(): Boolean {
        val v1 = Matrix(1, n)
        val v2 = Matrix(1, n)

        for (k in 0 until n) {
            v1[0][k] = c * A[i][k] + s * A[j][k]
            v2[0][k] = -s * A[i][k] + c * A[j][k]
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