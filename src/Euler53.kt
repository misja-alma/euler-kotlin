import java.math.BigInteger

fun fac(from: Int, until: Int): BigInteger = if (from == until) BigInteger.ONE else fac(from - 1, until).times(BigInteger.valueOf(from.toLong()))

fun nOverM(n: Int, m: Int): BigInteger = fac(n, m).divide(fac(n - m, 0))

fun countBigCombinatorics(n: Int): Int =
    (1 .. n).map { m -> nOverM(n, m) }.count { it > BigInteger.valueOf(1000000) }

fun main() {
    val solution = (1 .. 100).map { countBigCombinatorics(it) }.sum()

    println("Solution: $solution")
}