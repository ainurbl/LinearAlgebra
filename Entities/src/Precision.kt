import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

object Precision {
    val scale = 20
    val bigScale = 1000
    val mc = MathContext(scale)
    val bigMc = MathContext(bigScale)
    val scaleEps = BigDecimal("0.00000001").setScale(scale, RoundingMode.HALF_UP)
}