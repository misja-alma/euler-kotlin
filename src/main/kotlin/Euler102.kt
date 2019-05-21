import kotlin.math.sign

fun main() {
    // The cross product determines the orientation of a point versus a line, because it is anticommutative.
    // For each triangle; for each combination of 2 points forming a line and the third point:
    // check using cross product that the origin has the same orientation towards the line as the third point.
    // So if 3rd point's cross product > 0, origin's cross product must be >= 0 and vice versa.
    // If this is true for all 3 point-line combinations, then the origin must lie within the triangle.

    val input = ClassLoader.getSystemResource("p102_triangles.txt").readText()

    val triangles = input.lines().map {
        val xs = it.split(",").map { it.toInt() }.toIntArray()
        arrayOf(point(xs[0], xs[1]), point(xs[2], xs[3]), point(xs[4], xs[5]))
    }
    val origin = point(0, 0)
    val solution = triangles.count { isWithinTriangle(it, origin) }

    println ("Solution: $solution")
}

typealias Point = Array<Int>

fun point(x: Int, y: Int): Point = arrayOf(x, y)

fun isWithinTriangle(triangle: Array<Point>, point: Point): Boolean {
    val linesWithPoints = arrayOf(
            Pair(arrayOf(triangle[0], triangle[1]), triangle[2]),
            Pair(arrayOf(triangle[0], triangle[2]), triangle[1]),
            Pair(arrayOf(triangle[1], triangle[2]), triangle[0])
    )

    return linesWithPoints.all { (ab, p) ->
        // take cross product AB x AP where AB is the line.
        val orgOrientation = crossProduct2D(pointsToVector(ab[0], ab[1]), pointsToVector(ab[0], point))
        val point3Orientation = crossProduct2D(pointsToVector(ab[0], ab[1]), pointsToVector(ab[0], p))
        orgOrientation == 0 || orgOrientation.sign == point3Orientation.sign
    }
}

fun pointsToVector(p1: Point, p2: Point): Array<Int> = arrayOf(p2[0] - p1[0], p2[1] - p1[1])

fun crossProduct2D(x: Array<Int>, y: Array<Int>): Int = x[0] * y[1] - x[1] * y[0]