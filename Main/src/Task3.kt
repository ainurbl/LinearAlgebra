import java.math.BigDecimal
import java.math.RoundingMode

class Task3 : Task {

    var n = 0
    var A = Matrix(0, 0)
    var c = BigDecimal.ZERO
    var s = BigDecimal.ZERO
    var i = 0
    var j = 0

    var eps = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)

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
                A[i][j] = read.nextBigDecimal()
            }
        }
        i = read.nextInt() - 1
        j = read.nextInt() - 1
        c = read.nextBigDecimal()
        s = read.nextBigDecimal()
        assert((c * c + s * s - BigDecimal.ONE).abs() <= eps)
        assert(i < j && j <= n - 1 && i >= 0)
    }

    fun erase(x: Int, i1: Int, i2: Int) {
        i = i1
        j = i2
        val denom = (A[i1][x].pow(2) + A[i2][x].pow(2)).sqrt(Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
        c = A[i1][x].divide(denom, Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
        s = A[i2][x].divide(denom, Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
    }

    private var v1 = Matrix(1, n)
    private var v2 = Matrix(1, n)

    override fun execute(): Boolean {
        v1 = Matrix(1, n)
        v2 = Matrix(1, n)

        for (k in 0 until n) {
            v1[0][k] = (c * A[i][k] + s * A[j][k]).setScale(Precision.scale, RoundingMode.HALF_UP)
            v2[0][k] = (-s * A[i][k] + c * A[j][k]).setScale(Precision.scale, RoundingMode.HALF_UP)
        }
        for (k in 0 until n) {
            A[i][k] = v1[0][k].setScale(Precision.scale, RoundingMode.HALF_UP)
            A[j][k] = v2[0][k].setScale(Precision.scale, RoundingMode.HALF_UP)
        }
        return true
    }

    override fun output() {
        A.print()
    }

    fun set(gg: Pair<Pair<Int, Int>, Pair<BigDecimal, BigDecimal>>) {
        i = gg.first.first
        j = gg.first.second
        s = gg.second.first
        c = gg.second.second
    }
}