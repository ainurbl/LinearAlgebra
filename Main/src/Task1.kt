import java.math.BigDecimal

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
                A[i][j] = BigDecimal(read.nextDouble(), Precision.context)
            }
        }
        for (i in 0 until n) {
            b[i][0] = BigDecimal(read.nextDouble(), Precision.context)
        }
        eps = BigDecimal(read.nextDouble(), Precision.context)
    }

    private val uniteCircle = Circle(BigDecimal(0, Precision.context), BigDecimal(0, Precision.context), BigDecimal(1, Precision.context))

    fun circlesAreNotInUniteCircle() = !A.getCircles().all { circle -> circle.isInside(uniteCircle) }

    private var cantReach = false

    override fun execute(): Boolean {
        x = Matrix(n, 1)
        val bad = circlesAreNotInUniteCircle()
        var badCounter = 0
        do {
            val newX = A * x + b
            if (newX.F() >= x.F() + BigDecimal(1, Precision.context)) {
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