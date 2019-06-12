data class Counts(val nr0Empty: Long, val nr1Empty: Long, val nr2Empty: Long, val nr3Empty: Long, val totalCount: Long)

fun main() {
    val stateFor3 = Counts(1, 0, 0, 1, 2)

    val (_, finalCounts) = generateSequence(Pair(3, stateFor3)){ (length, state) -> Pair(length + 1, newState(state))}
            .dropWhile { it.first < 50 }
            .first()

    println ("Solution: ${finalCounts.totalCount}")
}

fun newState(state: Counts): Counts {
    val new0Empty = state.nr0Empty + state.nr3Empty
    val new1Empty = state.nr0Empty 
    val new2Empty = state.nr1Empty
    val new3Empty = state.nr2Empty + state.nr3Empty
    return Counts(new0Empty, new1Empty, new2Empty, new3Empty, state.totalCount + state.nr3Empty + state.nr0Empty)
}
