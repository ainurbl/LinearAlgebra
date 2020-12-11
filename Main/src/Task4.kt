import Matrix.Companion.I
import java.math.BigDecimal

class Task4 : Task {

    var n = 0
    var A = Matrix(0, 0)
    var Q = Matrix(0, 0)
    var R = Matrix(0, 0)

    private val accuracy = BigDecimal(0.00000000000001)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = BigDecimal(read.nextDouble())
            }
        }
    }

    override fun execute(): Boolean {
        val engine = Task3()
        engine.n = n
        engine.A = A
        Q = I(n)
        for (j in 0 until n) {
            var id = -1
            for (i in j until n) {
                if (!engine.A.isZero(i, j, accuracy)) {
                    id = i
                    break
                }
            }
            if (id == -1) continue
            for (i in id + 1 until n) {
                if (!engine.A.isZero(i, j, accuracy)) {
                    engine.erase(j, id, i)
                    Q = engine.getGivensRotation() * Q
                    engine.execute()
                }
            }
            if (id != j) {
                engine.i = j
                engine.j = id
                engine.c = BigDecimal.ZERO
                engine.s = BigDecimal.ONE
                Q = engine.getGivensRotation() * Q
                engine.execute()
            }
        }
        R = engine.A
        Q = Q.transpose()
        return true
    }

    override fun output() {
        println("Q = ")
        Q.print()
        println("R = ")
        R.print()
    }
}