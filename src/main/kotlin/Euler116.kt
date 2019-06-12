import java.util.*

fun main() {
    val solution = nrFillings(2, 50) + nrFillings(3, 50) + nrFillings(4, 50)

    println ("Solution: $solution")
}

fun nrFillings(blockSize: Int, totalSize: Int): Long {
    val emptyLefts = ArrayDeque<Long>()
    repeat (blockSize - 1) { emptyLefts.offer(0) }
    emptyLefts.offer(1)
    val stateForBlockSize = GenericCounts(emptyLefts, 1, 2)

    val (_, counts) = generateSequence(Pair(blockSize, stateForBlockSize)){ (length, state) -> Pair(length + 1, newState2(state))}
            .dropWhile { it.first < totalSize}
            .first()

    return counts.totalCount - 1 // Don't count the empty filling
}

// Slightly different state changes than in 114 and 115, because we have only one block size now
fun newState2(state: GenericCounts): GenericCounts {
    val new0Empty = state.nrBlockEmpty + state.emptyLefts.peek()
    val newBlockEmpty = state.emptyLefts.remove() + state.nrBlockEmpty
    state.emptyLefts.offer(new0Empty)
    return GenericCounts(state.emptyLefts, newBlockEmpty, state.totalCount + new0Empty)
}