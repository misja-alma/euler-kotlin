import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.util.*

fun toDigits(x: Int): List<Int> {
    val digits = LinkedList<Int>()
    var remain = x
    do {
        val digit = remain % 10
        remain /= 10
        digits.push(digit)
    } while (remain > 0)

    return digits
}

fun toDigits(x: Long): List<Int> {
    val digits = LinkedList<Int>()
    var remain = x
    do {
        val digit = remain % 10
        remain /= 10
        digits.push(digit.toInt())
    } while (remain > 0)

    return digits
}

fun fromDigits(ints: List<Int>): Long = if (ints.isEmpty()) 0L else ints.last() + 10 * fromDigits(ints.init())

fun gcd(x: Long, y: Long): Long {

    tailrec fun doGcd(big: Long, small: Long): Long {
        val remainder = big % small
        return if (remainder == 0L) small else doGcd(small, remainder)
    }

    val px = if (x < 0) -x else x
    val py = if (y < 0) -y else y

    return if (px > py) doGcd(px, py) else doGcd(py, px)
}

fun gcd(x: BigInteger, y: BigInteger): BigInteger {

    tailrec fun doGcd(big: BigInteger, small: BigInteger): BigInteger {
        val remainder = big % small
        return if (remainder == BigInteger.ZERO) small else doGcd(small, remainder)
    }

    val px = if (x.signum() < 0) -x else x
    val py = if (y.signum() < 0) -y else y

    return if (px > py) doGcd(px, py) else doGcd(py, px)
}

fun sqrt(x: BigDecimal, nrDigits: Int): BigDecimal {
    val mathContext = MathContext(nrDigits)
    val errorMargin = BigDecimal(1).divide(BigDecimal(10).pow(nrDigits), mathContext)
    var xn = x.divide(BigDecimal(2), mathContext)
    var prev: BigDecimal
    do {
        prev = xn
        xn = (xn + x.divide(xn, mathContext)).divide(BigDecimal(2), mathContext)
    } while ((prev - xn).abs() > errorMargin)

    return xn
}

fun divides(numerator: Long, divider: Long): Boolean = numerator.rem(divider) == 0L

class PrimeCache {
    private val cache = sortedSetOf(2L, 3L)

    fun contains(x: Long): Boolean = cache.contains(x)

    fun last(): Long = cache.last()!!

    fun sequence(): Sequence<Long> = cache.iterator().asSequence()

    fun add(x: Long) { cache.add(x) }
}

fun primes(cache: PrimeCache): Sequence<Long> {
    val largestKnown = cache.last()
    // TODO could be optimized even more, because this assumes that cache will only be updated within this method
    // In reality it could be updated elsewhere too. To use that to our advantage, we should check in every call to next
    // if we are within the bounds of our cache or not.
    return cache.sequence() + generateSequence(largestKnown + 2) { it + 2 }.filter { isPrime(it, cache) }
}

fun isPrime(x: Long, cache: PrimeCache): Boolean =
        if (x <= 1) false else if (cache.contains(x)) true else if (cache.last() >= x) false else {
            // First check all primes in the cache. When not found, continue expanding the cache until > sqrt x
            if (cache.sequence().any { divides(x, it) }) false else {
                var counter = cache.last() + 2
                var dividerFound = false
                while (!dividerFound && counter * counter <= x) {
                    dividerFound = if (isPrime(counter, cache)) {
                        cache.add(counter)
                        divides(x, counter)
                    } else {
                        false
                    }
                    counter += 2
                }
                !dividerFound
            }
        }

fun primesBySieve(maxPrime: Int): List<Int> =
        if (maxPrime < 2) listOf() else {
            val marked = BooleanArray(maxPrime  - 1) // Start counting at 2
            val result = mutableListOf<Int>()
            var nextPrimeIndex = marked.indices.find { !marked[it] }
            while (nextPrimeIndex != null) {
                val nextPrime = nextPrimeIndex + 2
                result += nextPrime
                for (i in nextPrime.toLong() * nextPrime .. maxPrime step nextPrime.toLong()) marked[i.toInt() - 2] = true
                nextPrimeIndex = (nextPrimeIndex + 1 .. maxPrime - 2).find { !marked[it] }
            }

            result
        }

fun primesBySieve(from: Long, segmentSize: Int, rootPrimes: List<Int>): List<Long> {
    val marked = BooleanArray(segmentSize)
    // Skip prime 2
    rootPrimes.drop(1).forEach {
        var i = from + it - from % it
        do {
            marked[(i - from).toInt()] = true
            i += it
        } while (i < from + segmentSize)
    }

    val result = mutableListOf<Long>()
    val start = if (from % 2L == 0L) from + 1 else from
    for (i in start until from + segmentSize step 2) if (!marked[(i - from).toInt()]) result.add(i)

    return result
}

fun pow(x: Long, to: Int): Long = if (to == 0) 1L else x * pow(x, to - 1)