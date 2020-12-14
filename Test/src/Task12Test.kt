import Matrix.Companion.nextMatrix
import Matrix.Companion.nextSymMatrix
import Matrix.Companion.nextTridiagMatrix
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode


internal class Task12Test {

    private val accuracy = BigDecimal(0.0001).setScale(Precision.scale, RoundingMode.HALF_UP)

    fun addEdge(matrix:Matrix, from: Int, to: Int) {
        matrix[from - 1][to - 1] = BigDecimal.ONE
        matrix[to - 1][from - 1] = BigDecimal.ONE
    }

    @Test
    fun `simple 1`() {
        val engine = Task12()
        val n = 3
        engine.n = n
        engine.A = Matrix(n,n)
        engine.A.set(listOf(0,1,0,1,0,1,0,1,0)) //   1 - 2 - 3
        engine.B = Matrix(n,n)
        engine.B.set(listOf(0,0,1,0,0,1,1,1,0)) // 1 - 3 - 2
        println( engine.execute())
    }


    @Test
    fun `simple 2`() {
        val engine = Task12()
        val n = 5
        engine.n = n
        engine.A = Matrix(n,n)
        engine.B = Matrix(n,n)
        println(engine.execute()) // графы без ребер изоморфны
    }

    @Test
    fun `simple 3`() {
        val engine = Task12()
        val n = 5
        engine.n = n
        engine.A = Matrix(n,n)
        engine.B = Matrix(n,n)
        engine.A[0][1] = BigDecimal.ONE
        engine.A[1][0] = BigDecimal.ONE // граф с ребром 1 - 2
        engine.B[3][2] = BigDecimal.ONE
        engine.B[2][3] = BigDecimal.ONE
        println(engine.execute())
    }

    @Test
    fun `simple 4`() {
        val engine = Task12()
        val n = 5
        engine.n = n
        engine.A = Matrix(n,n)
        engine.B = Matrix(n,n)
        addEdge(engine.A,1, 3) //      1 - 3 ----- 4
        addEdge(engine.A,1, 5) //      | /         |
        addEdge(engine.A,5, 2) //      5-----------2
        addEdge(engine.A,2, 4) //
        addEdge(engine.A,3, 4) //
        addEdge(engine.A, 3, 5) //

        addEdge(engine.B,1, 4) //      2-3--4
        addEdge(engine.B,1, 3) //      |/ \ |
        addEdge(engine.B,3, 4) //      5---1
        addEdge(engine.B,3, 2) //
        addEdge(engine.B,2, 5) //
        addEdge(engine.B,1, 5)
        addEdge(engine.B,3, 5)

        println(engine.execute())
    }
    @Test
    fun `simple 5`() {
        val engine = Task12()
        val n = 5
        engine.n = n
        engine.A = Matrix(n,n)
        engine.B = Matrix(n,n)


        addEdge(engine.B,1, 4) //      2-3--4
        addEdge(engine.B,1, 3) //      |/ \ |
        addEdge(engine.B,3, 4) //      5---1
        addEdge(engine.B,3, 2) //
        addEdge(engine.B,2, 5) //
        addEdge(engine.B,1, 5)
        addEdge(engine.B,3, 5)

        println(engine.execute())
    }




}