import java.math.BigDecimal
import java.math.RoundingMode

class Task7 : Task {

    var n = 0
    var A = CMatrix(0, 0)
    var x = CMatrix(0, 0)
    var eps = BigDecimal.ZERO
    var lambda = Complex(BigDecimal.ZERO, BigDecimal.ZERO)


    override fun input() {
        n = read.nextInt()
        A = CMatrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = Complex(read.nextBigDecimal(), BigDecimal.ZERO)
            }
        }
        x = CMatrix(n,1)
        for (i in 0 until n) {
            for (j in 0 until 1) {
                x[i][j] = Complex(read.nextBigDecimal(), BigDecimal.ZERO)
            }
        }
        eps = read.nextBigDecimal()
    }

    override fun execute(): Boolean {
        x *= BigDecimal.ONE.divide(x.F(), Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
        var xNew: CMatrix
        var cnt = 20
        do {
            xNew = A * x
            lambda =  (x.transpose()*xNew)[0][0]
            if ((xNew - x * lambda).F() <= eps) {
                (xNew - x * lambda).print()
                return true
            }
            xNew *= BigDecimal.ONE.divide(xNew.F(), Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
            assert((xNew.F() - BigDecimal.ONE).abs() <= eps)
            x = xNew
            cnt -= 1
        } while (cnt >= 0)
        return cnt >= 0
    }

    override fun output() {
        println(lambda)
        x.print()
    }
}