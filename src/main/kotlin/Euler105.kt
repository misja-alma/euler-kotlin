
fun main() {
    val input = ClassLoader.getSystemResource("p105_sets.txt").readText()

    val sets = input.lines().map {
        it.split(",").map { it.toInt() }.sorted()
    }

    val subsetCache = mutableMapOf<List<Int>, Boolean>()

    val specials = sets.filter { isSpecialSumSet(it, subsetCache) }
    val solution = specials.map { it.sum().toLong() }.sum()

    println ("Solution: $solution")
}


