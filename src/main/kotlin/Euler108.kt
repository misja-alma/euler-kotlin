import java.util.*

fun main() {
    // Iterate through possible N's by taking their prime factors as a starting point.
    // Because when we know the prime factors we know the nr of reciprocals;
    // Say x = n + a, y = n + b and a >=b, then rewrite the equation and do some algebra, we get a*b = n*n
    // Which means the nr. of distinct divisors of n squared equals the nr of reciprocals.
    // The nr of divisors of a number is the product of the sizes of all groups of prime divisors increased by one each.
    // E.g. nr of divisors of 6 = 2 * 2 = 4. Which matches the reality; 1,2,3,6
    // If the nr of divisors of n is x, the nr of distinct divisors of n*2 = x + x/2 - 1.
    // With all these we can get the nr of reciprocals immediately from knowing the prime divisors of n.
    // Algo; Use A* search in the space of prime divisors having the lowest product as heuristic; with same product the higher nr of divisors wins.
    // Search until solution with > 1000 found.
    // Build solutions by increasing each prime divisor group of a candidate with a new prime and adding a new one at the end.
    // Lengths of prime divisor groups can be limited because the most efficient group size to give the nr. of divisors is 2 primes per group;
    // So log 2 primes of the desired solution is more than enough, in particular because we favor adding small primes to give a small solution.
    // Taking into account that we take divisors from the squared n, we actually need no more than log 2 of 2n/3.
    // So log 2 of 512 = 9 primes should be more than enough.

    // NOTE instead of lists of groups of ints we could have a custom type and cache the product

    val candidateComparator = Comparator<List<Int>> { a, b ->
        val pa = primeProduct(a)
        val pb = primeProduct(b)
        // Smaller product is better, otherwise larger size wins and note that pq prefers the smallest element
        if (pa != pb) pa.compareTo(pb) else b.size.compareTo(a.size)
    }

    // For easier lookup, story prime indices in pq instead of primes themselves.
    // So a list(2,1) means twice prime 0 and once prime 1, i.e. list(2,2,3)
    val pq = PriorityQueue<List<Int>>(candidateComparator)
    pq.add(listOf(1))

    while(distinctDivisorsOfSquare(pq.peek()) <= 1000) {
        val next = pq.remove()
        val newCandidates = children(next)
        pq.addAll(newCandidates)
        checkedCandidates.addAll(newCandidates.map { primeProduct(it) })
    }

    val solution = pq.remove()

    println ("Solution: ${primeProduct(solution)}")
}

val checkedCandidates = mutableSetOf<Int>()

fun children(ls: List<Int>): List<List<Int>> {
    val withExtraElements = ls.indices
            .filter{ it == 0 || ls[it] < ls[it-1]}
            .map {
                val updated = ls.clone().toMutableList()
                updated[it] += 1
                updated
            }
            .filterNot { checkedCandidates.contains(primeProduct(it)) }
    return if (ls.size < primes.size) withExtraElements + listOf(ls.clone() + 1) else withExtraElements
}

val primes = intArrayOf(2,3,5,7,11,13,17,19,23)

fun primeProduct(ls: List<Int>): Int = ls.indices.map { pow(primes[it], ls[it]) }.product()

fun distinctDivisorsOfSquare(ls: List<Int>): Int =
    ls.map { it * 2 + 1 }.product() / 2 + 1

fun pow(x: Int, n: Int): Int {
    var result = 1
    (1 .. n).forEach { result *= x }
    return result
}

// Too slow solution but handy for debugging
fun printAll(): Int {
    fun reciprocals(n: Int): List<Pair<Int, Int>> =
            generateSequence(Pair(n, 0)) { (x, y) -> Pair(x + 1, if (((x + 1) * n).rem(x + 1 - n) == 0) ((x + 1) * n) / (x + 1 - n) else 0) }
                    .filter { it.second != 0 }
                    .takeWhile { it.second >= it.first }
                    .toList()

    val (solution, _) = generateSequence(Pair(1, 1)) { (n, _) ->
                val r = reciprocals(n + 1)
                println(r)
                Pair(n + 1, r.size)
            }
            .dropWhile {
                println("-----> ${it.first}: ${it.second}")
                it.second <= 1000
            }
            .iterator().next()

    return solution
}
