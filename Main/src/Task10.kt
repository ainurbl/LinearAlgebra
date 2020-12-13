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
        var RQ = Matrix(n, n)
        engine.A = A
        vs = I(n)

        do {
//            engine.A.print()
            engine.execute()
            RQ = fastMult(engine.R, engine.Q)
//            engine.Q.print()
//            engine.R.print()
//            (engine.Q * engine.R ).print()
            vs *= engine.Q
            engine.A = RQ

        } while (!engine.A.allCirclesAreLessThan(eps))
        A = engine.A
        return true
    }

    private fun fastMult(r: Matrix, q: Matrix): Matrix {
        val retMatrix = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                var adding = q[i][j] * r[i][i]
                if (i != n - 1) {
                    adding += r[i][i + 1] * q[i + 1][j]
                }
                if(i != n - 1 && i != n - 2) {
                    adding += r[i][i + 2] * q[i + 2][j]
                }
                retMatrix[i][j] = adding.setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
        return retMatrix
    }

    override fun output() {
        for (i in 0 until n) {
            print("${A[i][i]} ")
        }
        println()
        vs.print()
    }
}