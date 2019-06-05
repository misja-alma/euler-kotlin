fun main() {
    val sqrtTarget = Math.sqrt(9_999_999_999.0).toLong()

    val rootPrimes = primesBySieve(sqrtTarget.toInt())

    val maxRunLengths = IntArray(10)
    val maxRunWinners = LongArray(10)
    val segmentSize = 1_000_000L
    for (from in 1_000_000_000L until 10_000_000_000 step segmentSize) {

        val primes = primesBySieve(from, segmentSize.toInt(), rootPrimes)
        
        primes.forEach {
            val ds = toDigits(it)
            val runLengths = getMaxRunLengths(ds)
            runLengths.indices.forEach { d ->
                if (runLengths[d] > maxRunLengths[d]) {
                    maxRunLengths[d] = runLengths[d]
                    maxRunWinners[d] = it
                } else
                    if (runLengths[d] == maxRunLengths[d]) {
                        maxRunWinners[d] += it
                    }
            }
        }
    }

    val solution = maxRunWinners.sum()
    println("Solution: $solution")
}

fun getMaxRunLengths(cs: List<Int>): IntArray {
    val runLengths = IntArray(10)

    cs.forEach {
        runLengths[it] += 1
    }

    return runLengths
}
