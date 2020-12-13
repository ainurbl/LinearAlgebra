import Matrix.Companion.I
import java.math.BigDecimal

class Task8: Task {


    var n = 0
    var A = Matrix(0,0)
    var eps = BigDecimal.ZERO
    var vs = Matrix(0,0)

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
        assert(A.isSymmetrical(eps))
        val engine = Task6()
        engine.n = n
        var RQ = Matrix(n,n)
        engine.A = A
        vs = I(n)
        do {
            engine.execute()
            RQ = engine.R * engine.Q
            vs *= engine.Q
            engine.A = RQ
        } while (!engine.A.allCirclesAreLessThan(eps))
        A = engine.A
        return true
    }

    override fun output() {
        for (i in 0 until n) {
            print("${A[i][i]} ")
        }
        println()
        vs.print()
    }
}