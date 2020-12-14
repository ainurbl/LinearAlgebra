import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode

class Task12: Task {

    var n = 0
    var A = Matrix(0,0)
    var B = Matrix(0,0)

    var eps = BigDecimal(0.00001).setScale(Precision.scale, RoundingMode.HALF_UP)

    override fun input() {
        n = read.nextInt()
        A = Matrix(n,n)
        B = Matrix(n,n)
        for (i in 0 until n) {
            for (j in 0 until n) {
                A[i][j] = read.nextBigDecimal()
            }
        }
        for (i in 0 until n) {
            for (j in 0 until n) {
                B[i][j] = read.nextBigDecimal()
            }
        }
    }

    fun toTridiag(matrix:Matrix): Matrix {
        val tt = Task9()
        tt.n = matrix.rows
        tt.A = matrix
        tt.execute()
        return tt.B
    }

    override fun execute(): Boolean {
        try {
            if (edgesAreDifferent())return false
            if (degreesAreDifferent())return false
            A = toTridiag(A)
            B = toTridiag(B)
            val specA = getValues(A)
            val specB = getValues(B)
//            println((specA - specB).F())
            for (i in 0 until n) {
                if ((specA[i]-specB[i]).abs() >= BigDecimal(0.00001) )return false
            }
            return true
        } catch(e: Exception) {
            return true
        }
    }

    private fun degreesAreDifferent(): Boolean {
        val l1 = mutableListOf<Int>()
        val l2 = mutableListOf<Int>()
        for (i in 0 until n){
            var tt1 = BigDecimal.ZERO
            var tt2 = BigDecimal.ZERO
            for (j in 0 until n) {
                tt1 += A[i][j]
                tt2 += B[i][j]
            }
            l1.add(tt1.toInt())
            l2.add(tt2.toInt())
        }
        return l1.sorted() != l2.sorted()
    }

    private fun edgesAreDifferent(): Boolean {
        var tt = BigDecimal.ZERO
        for (i in 0 until n ) {
            for (j in 0 until n) {
                tt += A[i][j]
                tt -= B[i][j]
            }
        }
        return tt.abs() >= BigDecimal("0.0001")
    }

    private fun getValues(a: Matrix): List<BigDecimal> {
        val tt = Task11()
        tt.A = a
        tt.n = a.rows
        tt.eps = eps
        tt.execute()
        val ret = mutableListOf<BigDecimal>()
        for (i in 0 until n){
            ret.add(tt.A[i][i])
        }
        return ret.sorted()
    }

    override fun output() {
        println("execute returns an answer")
    }
}