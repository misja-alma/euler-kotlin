// For each prime p, generate n^p for all n up to p root 10^12 (some arbitrary limit)
// The highest power not exceeding 10^15 = 50 (for n == 2) so take primes up to 47
// filter the resulting powers on being a digitSum and add to a set.
// Sort the set into a list and return element nr 30.
fun main() {
    val limit = 1000_000_000_000_000L

    val allDigitSums = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47)
            .flatMap { digitSums(it, limit) }
            .toSet()
    val solutions = allDigitSums.sorted()

    println ("Solution: ${solutions[29]}")
}

fun digitSums(power: Int, limit: Long): List<Long> =
        generateSequence(2L) { it + 1 }
                .map { pow(it, power) }
                .takeWhile { it < limit }
                .filter { it >= 10L && isDigitSum(it) }
                .toList()

fun isDigitSum(x: Long): Boolean = isRootOf(x, toDigits(x).sum())

tailrec fun isRootOf(x: Long, maybeRoot: Int): Boolean =
        if (x == maybeRoot.toLong()) true else if (maybeRoot == 1 || x % maybeRoot != 0L) false else isRootOf(x / maybeRoot, maybeRoot)

