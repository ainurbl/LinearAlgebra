import Matrix.Companion.I
import java.math.BigDecimal
import java.math.RoundingMode

class Task9 : Task {

    var n = 0
    var A = Matrix(0, 0)

    var eps = BigDecimal(0.000001).setScale(Precision.scale, RoundingMode.HALF_UP)

    var B = Matrix(0,0)
    var Q = Matrix(0,0)

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
        assert(A.isSymmetrical(eps))
        val engine = Task5()
        val engineR = Task5()
        engine.n = n
        engineR.n = n
        engineR.A = I(n)
        engine.A = A
        val uu = Matrix(n, 1)
        var norm = BigDecimal(0)
        val u = Matrix(n, 1)
        val v = Matrix(n, 1)
        for (i in 0 until n - 2) {
            for (j in 0 until n) {
                if (j <= i) uu[j][0] = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP) else uu[j][0] = engine.A[j][i].setScale(Precision.scale, RoundingMode.HALF_UP)
            }
            norm = uu.F()
            if(norm <= BigDecimal(1e-20).setScale(Precision.scale, RoundingMode.HALF_UP)) continue
            for (j in 0 until n) {
                u[j][0] = uu[j][0].divide(norm, Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
            }
            u[i+1][0] -= BigDecimal.ONE.setScale(Precision.scale, RoundingMode.HALF_UP)
            norm = u.F()
            if(norm <= BigDecimal(1e-20).setScale(Precision.scale, RoundingMode.HALF_UP)) continue
            for (j in 0 until n) {
                v[j][0] = u[j][0].divide(norm, Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
            }
            for (j in 0 .. i) {
                v[j][0] = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)
            }
            engine.v = v
            engine.executeRight()
            engine.execute()
            engineR.v = v
            engineR.execute()
        }
        B = engine.A
        Q = engineR.A
        return true
    }

    override fun output() {
        B.print()
        Q.print()
    }
}