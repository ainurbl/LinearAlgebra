import java.math.BigDecimal

data class Circle(val x: BigDecimal, val y: BigDecimal, val r: BigDecimal) {

    fun isInside(bigCircle: Circle): Boolean {
        return distSquared(x, y, bigCircle.x, bigCircle.y).sqrt(Precision.mc) + r <= bigCircle.r
    }
}

fun distSquared(x1: BigDecimal, y1: BigDecimal, x2: BigDecimal, y2: BigDecimal) = (y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)