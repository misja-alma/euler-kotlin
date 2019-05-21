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
        val set = listOf(i)
        subsetCache += Pair(set, true)

        for (j in i + 1 .. maxElementSize) {
            val set2 = listOf(i, j)
            subsetCache += Pair(set2, true)
            pq.add(set2)
        }
    }

    while(pq.peek().size < 7) {
        val next = pq.remove()

        val start = next.last() + 1
        val end = next[0] + next[1] - 1
        for (i in start .. end) {
            if (formsSpecialSubset(next, i, subsetCache)) {
                pq.add(clone(next) + i)
            }
        }
    }

    val solution = pq.remove()

    // 20313839404245
    println ("Solution: ${canonicalString(solution)}")
}


fun <T> List<T>.init(): List<T> = take(this.size - 1)

fun clone(ls: List<Int>): List<Int> = ls.map { it }

fun canonicalString(ls: List<Int>): String = ls.joinToString ("") { it.toString() }

// make all combo's of new el with existing sets of size - 1 and check individual validity of each
// check also that all the sums that the new element can make don't exist yet in any subgroup
// and check all the full splits of the new set including i to see that bigger sized splits have higher sums
// if the earlier validations succeed then it's enough to just compare the 'init' with the 'tail' where init and tail span the full set and init.size = tail.size + 1
// This must also cover the case of equal sums I guess.
// if valid, construct new list by appending and add to pq, add also to valid subset cache
fun formsSpecialSubset(set: List<Int>, newElement: Int, subsetCache: MutableMap<List<Int>, Boolean>): Boolean {
    val smallerSubsets = oneSmallerSubsets(set)
    val newList = clone(set) + newElement

    val result = smallerSubsets.all {
                isSpecialSubset(clone(it) + newElement, subsetCache)
            }
            && formsUniqueSums(set, newElement)
            && hasValidSplit(newList)

    subsetCache += Pair(newList, result)
    return result
}

fun hasValidSplit(set: List<Int>): Boolean  =
    if (set.size.rem(2) == 0) true else {
        val split = set.size / 2
        set.subList(0, split + 1).sum() > set.subList(split + 1, set.size).sum()
    }

fun oneSmallerSubsets(set: List<Int>): List<List<Int>> =
        (0 until set.size).map { i ->
            set.subList(0,  i) + set.subList(i + 1, set.size)
        }

fun isSpecialSubset(ls: List<Int>, cache: MutableMap<List<Int>, Boolean>): Boolean =
    if (cache.containsKey(ls)) cache[ls]!! else formsSpecialSubset(ls.init(), ls.last(), cache)

fun <T> List<T>.subLists(size: Int): Set<List<T>> =
        (0..this.size - size).flatMap { i ->
            val el = listOf(this[i])
            if (size == 1) {
                listOf(el)
            } else {
                this.subList(i + 1, this.size).subLists(size - 1).map { el + it }
            }
        }.toSet()

fun <T> List<T>.powerSet(): Set<List<T>> =
        (1..this.size).flatMap { this.subLists(it) }.toSet()

fun distinctSublistSums(ls: List<Int>): Set<Int> =
        ls.powerSet().map { it.sum() }.toSet()


fun formsUniqueSums(ls: List<Int>, x: Int): Boolean =
        ls.powerSet().all {
            val sum = it.sum() + x
            !distinctSublistSums(ls - it).contains(sum)
        }
