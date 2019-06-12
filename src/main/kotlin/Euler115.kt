import java.util.*

// Similar as 114, just use an array or a queue, we really are only interested in 0empty and m empty, the rest can be pushed on at every iteration.
data class GenericCounts(val emptyLefts: Deque<Long>,  val nrBlockEmpty: Long, val totalCount: Long)

fun main() {
    val blockSize = 50
    val emptyLefts = ArrayDeque<Long>()
    repeat (blockSize - 1) { emptyLefts.offer(0) }
    emptyLefts.offer(1)
    val stateForBlockSize = GenericCounts(emptyLefts, 1, 2)

    val (solution, _) = generateSequence(Pair(blockSize, stateForBlockSize)){ (length, state) -> Pair(length + 1, newState(state))}
            .dropWhile { it.second.totalCount <= 1000000}
            .first()

    println ("Solution: $solution")
}

fun newState(state: GenericCounts): GenericCounts {
    val old0Empty = state.emptyLefts.last
    val new0Empty = old0Empty + state.nrBlockEmpty
    val newBlockEmpty = state.emptyLefts.remove() + state.nrBlockEmpty
    state.emptyLefts.offer(new0Empty)
    return GenericCounts(state.emptyLefts, newBlockEmpty, state.totalCount + state.nrBlockEmpty + old0Empty)
}
