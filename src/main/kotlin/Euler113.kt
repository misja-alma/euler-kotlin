fun main() {
    val endings = LongArray(10)
    val targetExponent = 100

    for (i in 1 .. 9) endings[i] = 1L
    val decreasing = (1 until targetExponent ).fold(endings.sum()) { s, _ ->
        addDecreasingEndings(endings)
        s + endings.sum()
    }

    endings[0] = 0
    for (i in 1 .. 9) endings[i] = 1L
    val increasing = (1 until targetExponent ).fold(endings.sum()) { s, _ ->
        addIncreasingEndings(endings)
        s + endings.sum()
    }

    // Don't double-count repeated digits.
    println ("Solution: ${decreasing + increasing - targetExponent * 9}")
}

fun addDecreasingEndings(endings: LongArray) {
    val copy = LongArray(10)
    endings.copyInto(copy)
    copy.indices.forEach { i ->
        (i - 1 downTo 0).forEach {
            endings[it] += copy[i]
        } 
    }
}

fun addIncreasingEndings(endings: LongArray) {
    val copy = LongArray(10)
    endings.copyInto(copy)
    copy.indices.forEach { i ->
        (i + 1 .. 9).forEach {
            endings[it] += copy[i]
        }
    }
}
