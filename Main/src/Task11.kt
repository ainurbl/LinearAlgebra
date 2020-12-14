import Matrix.Companion.I
import java.math.BigDecimal
import java.math.RoundingMode

class Task11 : Task {
    var n = 0
    var A = Matrix(0, 0)
    var eps = BigDecimal.ZERO.setScale(Precision.scale, RoundingMode.HALF_UP)
    var vs = Matrix(0, 0)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n, n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal()
            }
        }
        eps = read.nextBigDecimal()
    }

    override fun execute(): Boolean {
        assert(A.isTridiagonal(eps))
        val engine = Task4(true)
        engine.n = n
        engine.A = A
        vs = I(n)
        var lastId = n - 1
        var s = BigDecimal.ZERO
        do {
            if (lastId == 0)break
            val underTheSqrt = ((engine.A[lastId][lastId] - engine.A[lastId-1][lastId-1]).pow(2) + BigDecimal(4)*engine.A[lastId][lastId-1]*engine.A[lastId-1][lastId]).setScale(Precision.scale, RoundingMode.HALF_UP)
            if (underTheSqrt <= BigDecimal.ZERO) {
                s = engine.A[lastId][lastId]
            }
            else {
                val under = underTheSqrt.sqrt(Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
                val t = engine.A[lastId][lastId] + engine.A[lastId - 1][lastId - 1]
                val t1 = (t + under).divide(BigDecimal(2), Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
                val t2 = (t - under).divide(BigDecimal(2), Precision.mc).setScale(Precision.scale, RoundingMode.HALF_UP)
                if ( (t1 - engine.A[lastId][lastId]).abs() >= (t2 - engine.A[lastId][lastId]).abs()){
                    s = t2
                }
                else {
                    s = t1
                }
            }
            if (lastId != n-1) engine.A[lastId+1][lastId+1].setScale(Precision.scale, RoundingMode.HALF_UP)
            for (i in 0..lastId) {
                engine.A[i][i] -= s
            }
            engine.execute()
            vs = multWithGivens(vs.transpose(), engine.lastGgs)
            engine.A = multWithGivens(engine.R.transpose(), engine.lastGgs).transpose()
            for (i in 0..lastId) {
                engine.A[i][i] += s
            }
            var canSkip = true
            for (i in 0 until lastId) {
                if (engine.A[i][lastId].abs() > Precision.scaleEps || engine.A[lastId][i].abs() > Precision.scaleEps)canSkip = false
            }
            if(canSkip) {
                lastId--
            }
        } while (!engine.A.allCirclesAreLessThan(eps))
        A = engine.A
        return true
    }

    val engine = Task3()
    private fun multWithGivens(matrix: Matrix, ggs: List<Pair<Pair<Int,Int>, Pair<BigDecimal, BigDecimal>>>): Matrix {
        engine.A = matrix
        engine.eps = eps
        engine.n = n
        for (gg in ggs) {
            engine.set(gg)
            engine.execute()
        }
        return engine.A.transpose()
    }

    override fun output() {
        for (i in 0 until n) {
            print("${A[i][i]} ")
        }
        println()
        vs.print()
    }
}