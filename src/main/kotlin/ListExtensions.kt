fun <T> List<T>.init(): List<T> = take(this.size - 1)

fun <T> List<T>.clone(): List<T> = this.map { it }

fun List<Int>.product(): Int =
        if (this.isEmpty()) throw IllegalStateException("No product exists over an empty list") else this.fold( 1, { acc, e -> acc * e } )

fun List<Long>.product(): Long =
        if (this.isEmpty()) throw IllegalStateException("No product exists over an empty list") else this.fold( 1L, { acc, e -> acc * e } )

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

fun <T> List<T>.swap(i1: Int, i2: Int): List<T> {
    val asMutable = this.toMutableList()
    asMutable[i1] = this[i2]
    asMutable[i2] = this[i1]
    return asMutable
}

fun <T> List<T>.permutations(): List<List<T>> {
    return when{
        this.size <= 1 -> listOf(this)
        else -> (0 until size).flatMap { i ->
            val swapped = swap(0, i)
            swapped.drop(1).permutations().map {
                listOf(swapped[0]) + it
            }
        }
    }
}



