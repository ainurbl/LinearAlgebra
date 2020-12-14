import Matrix.Companion.I
import java.math.BigDecimal
import java.math.RoundingMode

class Task10 : Task {
    var n = 0
    var A = Matrix(0, 0)
    var eps = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)
    var vs = Matrix(0, 0)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal()
            }
        }
        eps = read.nextBigDecimal()
    }

    override fun execute(): Boolean {
        assert(A.isTridiagonal(eps))
        val engine = Task4(true)
        engine.n = n
        engine.A = A
        vs = I(n)
        do {
            engine.execute()
            vs = multWithGivens(vs.transpose(), engine.lastGgs)
            engine.A = multWithGivens(engine.R.transpose(), engine.lastGgs).transpose()
        } while (!engine.A.allCirclesAreLessThan(eps))
        A = engine.A
        return true
    }

    val engine = Task3()
    private fun multWithGivens(matrix: Matrix, ggs: List<Pair<Pair<Int,Int>, Pair<BigDecimal, BigDecimal>>>): Matrix {
        engine.A = matrix
        engine.eps = eps
        engine.n = n
        for (gg in ggs) {
            engine.set(gg)
            engine.execute()
        }
        return engine.A.transpose()
    }

    override fun output() {
        for (i in 0 until n) {
            print("${A[i][i]} ")
        }
        println()
        vs.print()
    }
}