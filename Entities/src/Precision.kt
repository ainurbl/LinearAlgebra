import java.math.MathContext

object Precision {
    val scale = 20
    val bigScale = 1000
    val mc = MathContext(scale)
    val bigMc = MathContext(bigScale)
}