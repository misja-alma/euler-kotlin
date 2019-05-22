fun main() {
    // For all disjoint subset combinations of equal size:
    // Determine whether for each el of s1 there is an el in s2 that is bigger/ smaller
    // If not, then it has to be compared for equality.

    println ("Solution: ${nrOfPotentiallyEqualSubsets(12)}")
}

fun nrOfPotentiallyEqualSubsets(size: Int): Int {
    val dummyList = (1 .. size).toList()

    return dummyList.powerSet().map { s1 ->
        (dummyList - s1).subLists(s1.size).count { s2 ->
            potentiallyEqual(s1, s2)
        }
    }.sum() / 2      // Don't count duplicates
}

fun potentiallyEqual(s1: List<Int>, s2: List<Int>): Boolean =
    if (s1.isEmpty()) false else {
        val (high, low) = if (s1.last() < s2.last()) s2 to s1 else s1 to s2

        !(high.zip(low)).all { (h, l) -> h > l }
    }
