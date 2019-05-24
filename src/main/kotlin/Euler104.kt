import java.math.BigInteger

fun main() {
    val indexSequence = generateSequence (1) { it + 1 }
    val (_, solution) = moduloFibonacci(1000000000).zip(indexSequence).find { (f, i) ->
        isPandigit(f.toString()) && isPandigit(fibRecursive(i).toBigInteger().toString().substring(0, 9))
    }!!

    println ("Solution: $solution")
}

fun isPandigit(s: String): Boolean {
    val set = s.toSet()
    return set.size == 9 && !set.contains('0')
}

fun moduloFibonacci(modulo: Long): Sequence<Long> = generateSequence (Pair(1L, 1L)) { (x1, x2) -> Pair (x2, (x1 + x2).rem(modulo)) }.map { it.first }

const val precision = 500000
val cache2 = mutableMapOf(0 to BigIntWithPrecision(BigInteger.ZERO, precision),
        1 to BigIntWithPrecision(BigInteger.ONE, precision))
fun fibRecursive(n: Int): BigIntWithPrecision = cache2[n] ?: {
    val result = if (n.rem(2) == 1) {
        val f1 = fibRecursive((n + 1)/2)
        val f2 = fibRecursive(n / 2)
        f1 * f1 + f2 * f2
    } else {
        val f1 = fibRecursive(n/2)
        val f2 = fibRecursive(n / 2 - 1)
        (f2.shiftLeft(1) + f1) * f1
    }
    cache2[n] = result
    result
}()

class BigIntWithPrecision(private val i: BigInteger, private val significantBits: Int, private val shiftedRight: Int = 0) {

    private fun rescale(): BigIntWithPrecision = if (significantBits >= i.bitLength()) this else {
        val dif = i.bitLength() - significantBits
        BigIntWithPrecision(i.shiftRight(dif), significantBits, shiftedRight + dif)
    }

    private fun sameScaleAndSignificance(x: BigIntWithPrecision, y: BigIntWithPrecision): Pair<BigIntWithPrecision, BigIntWithPrecision> {
        val precision = Math.min(x.significantBits, y.significantBits)
        return if (x.shiftedRight < y.shiftedRight) {
            Pair(BigIntWithPrecision(x.i, precision, x.shiftedRight), BigIntWithPrecision(y.i.shiftLeft(y.shiftedRight - x.shiftedRight), precision, x.shiftedRight))
        } else {
            Pair(BigIntWithPrecision(x.i.shiftLeft(x.shiftedRight - y.shiftedRight), precision, y.shiftedRight), BigIntWithPrecision(y.i, precision, y.shiftedRight))
        }
    }

    // The result will have the least significant bits of both
    operator fun plus(y: BigIntWithPrecision): BigIntWithPrecision {
        val (left, right) = sameScaleAndSignificance(this, y)
        return BigIntWithPrecision(left.i + right.i, left.significantBits, left.shiftedRight).rescale()
    }

    // The result will have the least significant bits of both
    operator fun times(y: BigIntWithPrecision): BigIntWithPrecision {
        val (left, right) = sameScaleAndSignificance(this, y)
        return BigIntWithPrecision(left.i * right.i, left.significantBits, left.shiftedRight).rescale()
    }

    fun shiftLeft(dist: Int): BigIntWithPrecision {
        return BigIntWithPrecision(i.shiftLeft(dist), significantBits, shiftedRight).rescale()
    }

    fun toBigInteger(): BigInteger = i.shiftLeft(shiftedRight)
}
