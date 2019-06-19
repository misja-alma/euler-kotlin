fun main() {
    val limit = 100_000_000

    val maxCandidateStart = Math.sqrt(limit.toDouble()).toInt()
    val solution = (1 .. maxCandidateStart).flatMap { start ->
        val second = start + 1
        generateSequence(Pair(second, start * start + second * second)) { (i, s) ->
            val newNr = i + 1
            Pair(newNr, s + newNr * newNr)
        }
        .takeWhile { it.second < limit }
        .filter { isPalindrome(it.second) }
        .map { it.second.toLong() }
        .toSet()
    }.toSet().sum()

    println("Solution: $solution")
}

fun isPalindrome(x: Int): Boolean {
    val s = x.toString()
    return s == s.reversed()
}
