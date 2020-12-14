import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class Task13b: Task {

    var p = 0

    var eps = BigDecimal(0.00001).setScale(Precision.scale, RoundingMode.HALF_UP)

    private var G = Matrix(0,0)
    private var alpha = 0.0

    override fun input() {
        p = read.nextInt()
    }

    fun takeModulo(t: Int): Int {
        var r = t % p
        r += p
        r %= p
        return r
    }

    fun getCoordinate(x: Int): Int {
        return takeModulo(x)
    }

    fun connect(x: Int, nx:Int) {
        G[getCoordinate(x)][getCoordinate(nx)] += BigDecimal.ONE
    }

    fun inversed(x: Int): Int {
        for (y in 0 .. p - 1) {
            if((x * y)%p == 1 )return y
        }
        throw Exception()
    }

    fun makeEdges(x: Int) {
        if(x == p) {
            G[p][p] += BigDecimal(2)
            G[p][0] += BigDecimal.ONE
        }
        else if(x == 0) {
            connect(x, x + 1)
            connect(x, x - 1)
            G[x][p] += BigDecimal.ONE
        }
        else {
            connect(x, x+ 1)
            connect(x, x- 1)
            connect(x, inversed(x))
        }
    }

    fun getD():Int {
        return 3
    }

    fun toTridiag(matrix:Matrix): Matrix {
        val tt = Task9()
        tt.n = matrix.rows
        tt.A = matrix
        tt.execute()
        return tt.B
    }

    private fun getValues(a: Matrix): List<BigDecimal> {
        val tt = Task11()
        tt.A = a
        tt.n = a.rows
        tt.eps = BigDecimal(0.001)
        tt.execute()
        val ret = mutableListOf<BigDecimal>()
        for (i in 0 until a.rows){
            ret.add(tt.A[i][i])
        }
        return ret.sorted()
    }


    override fun execute(): Boolean {
        G = Matrix(p + 1,p + 1)

        for (x in 0..p) {
            makeEdges(x)
        }

        G = toTridiag(G)

        val list: List<BigDecimal>

        list = getValues(G)

        val dd = getD()

        alpha = Math.max(list[p + 1 -2].toDouble().absoluteValue, list[0].toDouble().absoluteValue)/dd
        return true
    }

    override fun output() {
        println("alpha = ${alpha}")
    }
}
