import java.lang.Math.sqrt

data class Circle(val x: Double, val y: Double, val r: Double) {
    fun isInside(bigCircle: Circle): Boolean {
        return sqrt(distSquared(x,y,bigCircle.x,bigCircle.y)) + r <= bigCircle.r
    }
}

fun distSquared(x1: Double, y1: Double, x2: Double, y2: Double) = (y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)