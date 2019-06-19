fun main() {
    val primeCache = PrimeCache()

    val solution = countDistinctPrimes((1..9).toList(), primeCache)

    println ("Solution: $solution")
}

fun leftSubLists(ls: List<Int>): List<List<Int>> = when(ls.size) {
    0 -> listOf()
    1 -> listOf(ls)
    else -> ls.drop(1).powerSet().map { listOf(ls[0]) + it } + listOf(listOf(ls[0]))
}

fun countDistinctPrimes(leftOver: List<Int>, primeCache: PrimeCache): Long =
    leftSubLists(leftOver).map { s ->
        val primeCount = s.permutations().count { isPrime(fromDigits(it), primeCache) }
        if (primeCount == 0) 0L else {
            val newLeftOver = leftOver - s
            if (newLeftOver.isEmpty()) {
                primeCount.toLong()
            } else primeCount * countDistinctPrimes(newLeftOver, primeCache)
        }
    }.sum()
