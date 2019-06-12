fun isPermutedMultiple(nrMultiples: Int, x: Int): Boolean {
    val digits = toDigits(x).toSet()
    return (2 .. nrMultiples).all { m -> toDigits(x * m).toSet() == digits}
}

fun main() {
    val start = System.currentTimeMillis()

    val solution: Int? = generateSequence(1) { it + 1 }.find { isPermutedMultiple(6, it) }

    val time = System.currentTimeMillis() - start

    println ("Solution: $solution in $time ms")
}