import java.math.BigDecimal
import java.math.RoundingMode

class Task1 : Task { // x_{i+1} = Ax_{i} + b
    var n = 0
    var A = Matrix(0, 0)
    var b = Matrix(0, 0)
    var eps = BigDecimal.ZERO
    var x = Matrix(0, 0)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        b = Matrix(n, 1)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal() .setScale(Precision.scale, RoundingMode.HALF_UP)
            }
        }
        for (i in 0 until n) {
            b[i][0] = read.nextBigDecimal() .setScale(Precision.scale, RoundingMode.HALF_UP)
        }
        eps = read.nextBigDecimal() .setScale(Precision.scale, RoundingMode.HALF_UP)
    }

    private val uniteCircle = Circle(BigDecimal.ZERO .setScale(Precision.scale, RoundingMode.HALF_UP), BigDecimal.ZERO .setScale(Precision.scale, RoundingMode.HALF_UP), BigDecimal.ONE .setScale(Precision.scale, RoundingMode.HALF_UP))

    fun circlesAreNotInUniteCircle() = !A.getCircles().all { circle -> circle.isInside(uniteCircle) }

    private var cantReach = false

    override fun execute(): Boolean {
        x = Matrix(n, 1)
        val bad = circlesAreNotInUniteCircle()
        var badCounter = 0
        do {
            val newX = A * x + b
            if (newX.F() >= x.F() + BigDecimal.ONE.setScale(Precision.scale, RoundingMode.HALF_UP)) {
                badCounter++
            } else {
                badCounter = 0
            }
            if (bad && badCounter >= 20) {
                cantReach = true
                return false
            }
            x = newX
        } while ((x - A * x - b).F() >= eps)
        return true
    }

    override fun output() {
        if (cantReach) {
            println(0)
            println("Can not reach solution")
        } else {
            x.print()
        }
    }


}