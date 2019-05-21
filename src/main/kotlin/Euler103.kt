import java.util.*
import kotlin.math.sign

fun main() {
    // Do an A* search where the heuristic is the a function of the sum of the candidate and the size of the set; smaller sums and larger sizes are better.
    // A heuristic could be that smaller sum is better but for each extra element the larger set can deduct the highest el + 1 of the smaller set from its total.
    // This is possible because all subsets of a special subset are also special subsets.
    // So we can build up our subsets from smaller sets until we reach the first one with 7 elements.
    // For each subset, just try all highest nrs not part of the set; combine the new nr. with all n-1 subsets of the current set and check that they are special sets and
    // that none of the new sums already exists. Note that this check can be done recursively.
    // This can be sped up by caching known special sets.
    // To prevent trying endlessly larger new candidates, before adding a new candidate Xn we can do a simple check that Xn < X0 + X1.

    val optimumSubsetHeuristic = Comparator<List<Int>> { a, b ->
        val sizeDif = a.size - b.size
        val (sumA, sumB) = if (sizeDif < 0) Pair(a.sum() - sizeDif * (a.last() + 1), b.sum()) else Pair(a.sum(), b.sum() + sizeDif * (b.last() + 1))
        (sumA - sumB).sign
    }

    val maxElementSize = 100
    val subsetCache = mutableMapOf<List<Int>, Boolean>()

    val pq = PriorityQueue<List<Int>>(optimumSubsetHeuristic)

    // Initialize pq with all 1 and 2 element sets because they are all special subsets
    for (i in 1 until maxElementSize) {
        for (j in i + 1 .. maxElementSize) {
            val set2 = listOf(i, j)
            pq.add(set2)
        }
    }

    while(pq.peek().size < 7) {
        val next = pq.remove()

        val start = next.last() + 1
        val end = next[0] + next[1] - 1
        for (i in start .. end) {
            if (formsSpecialSumSet(next, i, subsetCache)) {
                pq.add(clone(next) + i)
            }
        }
    }

    val solution = pq.remove()

    // 20313839404245
    println ("Solution: ${canonicalString(solution)}")
}


fun canonicalString(ls: List<Int>): String = ls.joinToString ("") { it.toString() }
