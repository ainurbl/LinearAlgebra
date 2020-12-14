import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class Task13a: Task {

    var n = 0

    var eps = BigDecimal(0.00001).setScale(Precision.scale, RoundingMode.HALF_UP)

    private var G = Matrix(0,0)
    private var alpha = 0.0

    override fun input() {
        n = read.nextInt()
    }

    fun takeModulo(t: Int): Int {
        var r = t % n
        r += n
        r %= n
        return r
    }

    fun getCoordinate(x: Int, y:Int): Int {
        return takeModulo(x) + n*takeModulo(y)
    }

    fun connect(x: Int, y:Int, nx:Int, ny:Int) {
        G[getCoordinate(x,y)][getCoordinate(nx, ny)] += BigDecimal.ONE
    }

    fun makeEdges(x: Int, y: Int) {
        var nx = x + 2*y
        var ny = y
        connect(x,y,nx,ny)
        nx = x - 2*y
        connect(x,y,nx,ny)
        nx = x + 2*y + 1
        connect(x,y,nx,ny)
        nx = x -2 *y - 1
        connect(x,y,nx,ny)
        nx = x
        ny = y + 2*x
        connect(x,y,nx,ny)
        ny = y - 2*x
        connect(x,y,nx,ny)
        ny = y + 2*x + 1
        connect(x,y,nx,ny)
        ny = y - 2*x - 1
        connect(x,y,nx,ny)
    }

    fun getD():Int {
        return 8
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
        for (i in 0 until n*n){
            ret.add(tt.A[i][i])
        }
        return ret.sorted()
    }


//    @ExperimentalTime
    override fun execute(): Boolean {
        G = Matrix(n*n,n*n)
        for (y in 0 until n) {
            for (x in 0 until n) {
                makeEdges(x,y)
            }
        }
//        val t = measureTime {
            G = toTridiag(G)
//        }
//        println("time for tridiag = ${t.inMilliseconds}")
        var list: List<BigDecimal>
//        val d = measureTime {
            list = getValues(G)
//        }
//        println("time for spectre = ${d.inMilliseconds}")
//        println(list)
        val dd = getD()
//        println("d = ${dd}")
        alpha = Math.max(list[n*n-2].toDouble().absoluteValue, list[0].toDouble().absoluteValue)/dd
        return true
    }

    override fun output() {
        println("alpha = ${alpha}")
    }
}

