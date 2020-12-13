import Matrix.Companion.I
import java.math.BigDecimal
import java.math.RoundingMode

class Task6 : Task {

    var n = 0
    var A = Matrix(0, 0)
    var Q = Matrix(0, 0)
    var R = Matrix(0, 0)

    var eps = BigDecimal(0.00000001, Precision.mc)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal()
            }
        }
    }


    override fun execute(): Boolean {
        val engine = Task5()
        engine.n = n
        engine.A = A
        val qEngine = Task5()
        qEngine.n = n
        qEngine.A = I(n)
        var v = Matrix(n, 1)
        for (j in 0 until n - 1) {
            for (i in 0 until n) {
                if (i < j) {
                    v[i][0] = BigDecimal.ZERO
                }
                else {
                    v[i][0] = engine.A[i][j]
                }
            }
            if (v.F() <= eps) continue
            v *= BigDecimal.ONE.divide(v.F(), Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
            v[j][0] -= BigDecimal.ONE
            v *= BigDecimal.ONE.divide(v.F(), Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
//            v.print()
            engine.v = v
            qEngine.v = v

            engine.execute()
            qEngine.execute()
        }
        Q = qEngine.A.transpose()
        R = engine.A
//        Q.print()
//        R.print()
        return true
    }

    override fun output() {
        println("Q = ")
        Q.print()
        println("R = ")
        R.print()
    }
}