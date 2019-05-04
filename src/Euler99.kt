import kotlin.math.sign

// If we have numbers x and y, y can be written as x ^ xLog (y)
// If we want to see if x^a > y^b, we write first y as a power of x, so y^b = x^ b * xLog(y)
// Then y^b / x^a = x ^ b * xLog(y) / x ^ a = x ^ (b*xLog(y) - a).
// If this is > 1 then y^b is bigger, < 1 then x^a is bigger == 1 means they are equal.
// If a power of a number > 1 that just means that the exponent > 0, so in the end we just want to know if b * xLog(y) - a > 0

fun comparePowers(x: Int, px: Int, y: Int, py: Int): Int {
    // Convert xLog(y) to a natural log: xLog(y) = ln(y)/ln(x)
    val xlogy = Math.log(y.toDouble()) / Math.log(x.toDouble())
    return -(py * xlogy - px).sign.toInt()
}

fun main() {
    val start = System.currentTimeMillis()

    val text = ClassLoader.getSystemResource("p099_base_exp.txt").readText()

    val powerTuples = text.lines()
        .map {
            it.split(",")
                .map { s -> Integer.parseInt(s) }
        }

    val powerComparator = Comparator<Pair<List<Int>, Int>> { (a, _), (b, _) -> comparePowers(a[0], a[1], b[0], b[1]) }

    val sorted = powerTuples.zip(powerTuples.indices).sortedWith(powerComparator)
    val (power, index) = sorted.last()

    val time = System.currentTimeMillis() - start

    println ("Solution: $power at line ${index + 1} in $time ms")
}
