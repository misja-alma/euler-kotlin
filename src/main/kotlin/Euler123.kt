fun main() {
    val primeCache = PrimeCache()
    val (_, solution) = primes(primeCache).zip(generateSequence (1){ it + 1 })
            .filterNot { it.second % 2 == 0}
            .find { (p, i) ->
        val sumOfPowers = 2 * p * i     // See Euler120 for the explanation of the reduction
        sumOfPowers % (p * p) > 10000000000
    }!!

    println ("Solution: $solution")
}