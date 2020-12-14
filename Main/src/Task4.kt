import Matrix.Companion.I
import java.math.BigDecimal
import java.math.RoundingMode

class Task4(private val isTridiag: Boolean = false) : Task {

    var n = 0
    var A = Matrix(0, 0)
    var Q = Matrix(0, 0)
    var R = Matrix(0, 0)

    private var eps = BigDecimal(0.00000001, Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal()
            }
        }
    }

    private val engine = Task3()
    private val Qengine = Task3()

    val lastGgs = mutableListOf<Pair<Pair<Int, Int>, Pair<BigDecimal, BigDecimal>>>()

    override fun execute(): Boolean {
        lastGgs.clear()
        engine.n = n
        engine.A = A
        engine.eps = eps
        Qengine.n = n
        Qengine.eps = eps

        Qengine.A = I(n)
        for (j in 0 until n) {
            var id = -1
            for (i in j until n) {
                if (!engine.A.isZero(i, j, eps)) {
                    id = i
                    break
                }
                if (isTridiag && i > j + 1) break
            }
            if (id == -1) continue
            for (i in id + 1 until n) {
                if (isTridiag && i > j + 1) break
                if (!engine.A.isZero(i, j, eps)) {
                    engine.erase(j, id, i)
                    Qengine.getFromOther(engine)
                    Qengine.execute()
                    engine.execute()
                    lastGgs.add(Pair(Pair(Qengine.i, Qengine.j), Pair(Qengine.s, Qengine.c)))
                }
            }
            if (id != j) {
                engine.i = j
                engine.j = id
                engine.c = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)
                engine.s = BigDecimal.ONE.setScale(Precision.scale, RoundingMode.HALF_UP)
                Qengine.getFromOther(engine)

                Qengine.execute()
                engine.execute()

                lastGgs.add(Pair(Pair(Qengine.i, Qengine.j), Pair(Qengine.s, Qengine.c)))
            }
        }
        R = engine.A
        Q = Qengine.A.transpose()
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