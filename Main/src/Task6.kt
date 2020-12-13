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
        TODO("Not yet implemented")
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
                    v[i][0] = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)
                }
                else {
                    v[i][0] = engine.A[i][j].setScale(Precision.scale, RoundingMode.HALF_UP)
                }
            }
            if (v.F() <= eps) continue
            v *= BigDecimal.ONE.setScale(Precision.scale, RoundingMode.HALF_UP).divide(v.F(), Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
            v[j][0] -= BigDecimal.ONE
            v *= BigDecimal.ONE.setScale(Precision.scale, RoundingMode.HALF_UP).divide(v.F(), Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
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
        TODO("Not yet implemented")
    }
}