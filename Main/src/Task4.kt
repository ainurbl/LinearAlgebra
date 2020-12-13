import Matrix.Companion.I
import java.math.BigDecimal
import java.math.RoundingMode

class Task4 : Task {

    var n = 0
    var A = Matrix(0, 0)
    var Q = Matrix(0, 0)
    var R = Matrix(0, 0)

    private val accuracy = BigDecimal(0.0000001)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal().setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
    }

    override fun execute(): Boolean {
        val engine = Task3()
        engine.n = n
        engine.A = A
        val Qengine = Task3()
        Qengine.n = n
        Qengine.A = I(n)
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
                    Qengine.getFromOther(engine)
                    Qengine.execute()
                    engine.execute()
                }
            }
            if (id != j) {
                engine.i = j
                engine.j = id
                engine.c = BigDecimal.ZERO .setScale(Precision.scale, RoundingMode.HALF_UP)
                engine.s = BigDecimal.ONE .setScale(Precision.scale, RoundingMode.HALF_UP)
                Qengine.getFromOther(engine)

                Qengine.execute()
                engine.execute()
            }
        }
        R = engine.A
        Q = Qengine.A.transpose().copy()
//        R.print()
//        Q.print()
        return true
    }

    override fun output() {
        println("Q = ")
        Q.print()
        println("R = ")
        R.print()
    }
}