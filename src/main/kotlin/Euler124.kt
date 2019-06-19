fun main() {
    val primeCache = PrimeCache()
    val factorCache = mutableMapOf<Long, List<Long>>()

    val comparator = Comparator<Pair<Long, Long>> { (i1, r1), (i2, r2) ->
        if (r1 == r2) i1.compareTo(i2) else r1.compareTo(r2)
    }
    val sorted = (1L .. 100000)
            .map { it to primeFactors(it, factorCache, primeCache).distinct().product() }
            .sortedWith(comparator)

    println ("Solution: ${sorted[10000-1].first}")
}
