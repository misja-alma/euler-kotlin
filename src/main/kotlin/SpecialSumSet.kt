// For each split n, s-n:
// * s-n should be a specialsumset
// * each combination n + subset of (s-n) should:
// ** have a sum that is not in any subset of the remaining set
// ** have smaller sum than any subset of .. if nr elements is smaller, have bigger sum if nr. els is bigger
//    (The last condition holds automatically if we take n as the max element of s.)
// NOTE: requires that the set is sorted ascendingly
fun formsSpecialSumSet(set: List<Int>, newElement: Int, subsetCache: MutableMap<List<Int>, Boolean>): Boolean {
    val smallerSubsets = oneSmallerSubsets(set)
    val newList = set.clone() + newElement

    val result = smallerSubsets.all {
        isSpecialSumSet(it, subsetCache)
    }
            && obeysSumLaws(set, newElement)

    subsetCache += Pair(newList, result)
    return result
}


fun oneSmallerSubsets(set: List<Int>): List<List<Int>> =
        (0 until set.size).map { i ->
            set.subList(0,  i) + set.subList(i + 1, set.size)
        }

// NOTE: requires that the set is sorted ascendingly
fun isSpecialSumSet(ls: List<Int>, cache: MutableMap<List<Int>, Boolean>): Boolean =
        (ls.size < 3) || if (cache.containsKey(ls)) cache[ls]!! else formsSpecialSumSet(ls.init(), ls.last(), cache)

fun obeysSumLaws(ls: List<Int>, x: Int): Boolean =
        ls.powerSet().all { it1 ->
            val sum = it1.sum() + x
            val size1 = it1.size + 1
            (ls - it1).powerSet().all { it2 ->
                val sum2 = it2.sum()
                if (it2.size < size1) sum2 < sum else if (it2.size > size1) sum2 > sum else sum2 != sum
            }
        }
