class Task1 : Task { // x_{i+1} = Ax_{i} + b
    var n = 0
    var A = Matrix(0, 0)
    var b = Matrix(0, 0)
    var eps = 0.0
    var x = Matrix(0,0)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        b = Matrix(n, 1)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextDouble()
            }
        }
        for (i in 0 until n) {
            b[i][0] = read.nextDouble()
        }
        eps = read.nextDouble()
    }

    private val uniteCircle = Circle(0.0, 0.0, 1.0)

    fun circlesAreNotInUniteCircle() = !A.getCircles().all { circle -> circle.isInside(uniteCircle) }

    private var cantReach = false

    override fun execute() {
        x = Matrix(n, 1)
        val bad = circlesAreNotInUniteCircle()
        var badCounter = 0
        do {
            val newX = A * x + b
            if (newX.F() >= x.F() + 1) {
                badCounter++
            } else {
                badCounter = 0
            }
            if (bad && badCounter >= 20) {
                cantReach = true
                return
            }
            x = newX
        } while ((x - A * x - b).F() >= eps)
    }

    override fun output() {
        if (cantReach) {
            println(0)
            println("Can not reach solution")
        }
        else {
            x.print()
        }
    }


}