// We need to use a list here and nothing else. Different blocksizes just check different nr of empty spaces at the left end.
// list[i] has the nr of combinations with exactly i empty spaces at the left.
fun main() {
    val initialState = listOf<Long>(0, 1) // The counts of a single space; 0 have zero empty, 1 has 1 empty space.
    val (_, counts) = generateSequence (Pair(1, initialState)) { (length, state) -> length + 1 to generateNewCounts(state)}
            .dropWhile { it.first < 50 }
            .iterator().next()

    println ("Solution: ${counts.sum()}")
}

fun generateNewCounts(counts: List<Long>): List<Long> {
    fun emptyUntil(dist: Int): Long = counts.drop(dist - 1).sum()

    val new0Empty = emptyUntil(2) + emptyUntil(3) + emptyUntil(4)
    return listOf(new0Empty) + counts
}
