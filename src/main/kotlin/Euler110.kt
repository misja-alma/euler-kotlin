
import java.util.*

fun main() {
    // Same as 108, only now we need to use Long's for the prime products and we need to check more primes.

    val candidateComparator = Comparator<List<Int>> { a, b ->
        val pa = primeProductLong(a)
        val pb = primeProductLong(b)
        // Smaller product is better, otherwise larger size wins and note that pq prefers the smallest element
        if (pa != pb) pa.compareTo(pb) else b.size.compareTo(a.size)
    }

    // For easier lookup, story prime indices in pq instead of primes themselves.
    // So a list(2,1) means twice prime 0 and once prime 1, i.e. list(2,2,3)
    val pq = PriorityQueue<List<Int>>(candidateComparator)
    pq.add(listOf(1))

    while(distinctDivisorsOfSquareLong(pq.peek()) <= 4000000) {
        val next = pq.remove()
        // println("Removed ${next.indices.map { Pair(primesLong[it], next[it])}} primeProduct ${primeProductLong(next)} divisors ${distinctDivisorsOfSquareLong(next)}")
        val newCandidates = childrenLong(next)
        pq.addAll(newCandidates)
        checkedCandidatesLong.addAll(newCandidates.map { primeProductLong(it) })
    }

    val solution = pq.remove()

    println ("Solution: ${primeProductLong(solution)}")
}

val checkedCandidatesLong = mutableSetOf<Long>()

fun childrenLong(ls: List<Int>): List<List<Int>> {
    val withExtraElements = ls.indices
            .filter{ it == 0 || ls[it] < ls[it-1]}
            .map {
                val updated = ls.clone().toMutableList()
                updated[it] += 1
                updated
            }
            .filterNot { checkedCandidatesLong.contains(primeProductLong(it)) }
    return if (ls.size < primesLong.size) withExtraElements + listOf(ls.clone() + 1) else withExtraElements
}

val primesLong = intArrayOf(2,3,5,7,11,13,17,19,23,29,31,37)

fun primeProductLong(ls: List<Int>): Long = ls.indices.map { powLong(primesLong[it].toLong(), ls[it]) }.product()

fun distinctDivisorsOfSquareLong(ls: List<Int>): Long =
        ls.map { it.toLong() * 2 + 1 }.product() / 2 + 1

fun powLong(x: Long, n: Int): Long {
    var result = 1L
    (1 .. n).forEach { result *= x }
    return result
}