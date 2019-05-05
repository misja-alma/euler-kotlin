import java.math.BigInteger
import kotlin.math.sign

abstract class Fraction<T>(val numerator: T, val denominator: T): Comparable<Fraction<T>> {
    abstract operator fun plus(f2: Fraction<T>): Fraction<T>

    abstract operator fun minus(f2: Fraction<T>): Fraction<T>

    abstract operator fun times(f2: Fraction<T>): Fraction<T>

    abstract operator fun div(f2: Fraction<T>): Fraction<T>

    abstract fun simplify(): Fraction<T>

    abstract fun isWholeNumber(): Boolean

    abstract fun toIntegral(): T

    abstract fun toDouble(): Double

    abstract fun sign(): Int

    abstract fun isOne(): Boolean

    // TODO would be better if this could somehow be on each of the implementations' companion object
    abstract fun zero(): Fraction<T>

    // Note that hashcode does not need to be overridden because its contract with equals still holds
    abstract override fun equals(other: Any?): Boolean

    override fun toString(): String = "$numerator/$denominator"

    override fun compareTo(other: Fraction<T>): Int {
        val dif = this - other
        return dif.sign()
    }

    companion object {
        fun fraction(numerator: Long, denominator: Long = 1L): Fraction<Long> = LongFraction(numerator, denominator)

        fun fraction(numerator: BigInteger, denominator: BigInteger = BigInteger.ONE): Fraction<BigInteger> = BigIntegerFraction(numerator, denominator)
    }
}

class LongFraction(numerator: Long, denominator: Long): Fraction<Long>(numerator, denominator) {
    override fun zero(): Fraction<Long> = LongFraction(0L, 1L)

    override fun isOne(): Boolean = numerator != 0L && numerator == denominator

    override operator fun plus(f2: Fraction<Long>): Fraction<Long> =
        LongFraction(numerator * f2.denominator + f2.numerator * denominator, f2.denominator * denominator)

    override operator fun minus(f2: Fraction<Long>): Fraction<Long> =
        LongFraction(numerator * f2.denominator - f2.numerator * denominator, f2.denominator * denominator)

    override operator fun times(f2: Fraction<Long>): Fraction<Long> =
        LongFraction(numerator * f2.numerator, f2.denominator * denominator)

    override operator fun div(f2: Fraction<Long>): Fraction<Long> =
        LongFraction(numerator * f2.denominator, f2.numerator * denominator)

    override fun simplify(): Fraction<Long> {
        val divider = gcd(numerator, denominator)
        return LongFraction(numerator / divider, denominator / divider)
    }

    override fun isWholeNumber(): Boolean = numerator % denominator == 0L

    override fun toIntegral(): Long = numerator / denominator

    override fun toDouble(): Double = numerator.toDouble() / denominator.toDouble()

    override fun sign(): Int = numerator.sign * denominator.sign

    override fun equals(other: Any?): Boolean =
        other is LongFraction && compareTo(other) == 0
}

class BigIntegerFraction(numerator: BigInteger, denominator: BigInteger): Fraction<BigInteger>(numerator, denominator) {
    override fun zero(): Fraction<BigInteger> = BigIntegerFraction(BigInteger.ZERO, BigInteger.ONE)

    override fun isOne(): Boolean = numerator != BigInteger.ZERO && numerator == denominator

    override operator fun plus(f2: Fraction<BigInteger>): Fraction<BigInteger> =
        BigIntegerFraction(numerator * f2.denominator + f2.numerator * denominator, f2.denominator * denominator)

    override operator fun minus(f2: Fraction<BigInteger>): Fraction<BigInteger> =
        BigIntegerFraction(numerator * f2.denominator - f2.numerator * denominator, f2.denominator * denominator)

    override operator fun times(f2: Fraction<BigInteger>): Fraction<BigInteger> =
        BigIntegerFraction(numerator * f2.numerator, f2.denominator * denominator)

    override operator fun div(f2: Fraction<BigInteger>): Fraction<BigInteger> =
        BigIntegerFraction(numerator * f2.denominator, f2.numerator * denominator)

    override fun simplify(): Fraction<BigInteger> {
        val divider = gcd(numerator, denominator)
        return BigIntegerFraction(numerator / divider, denominator / divider)
    }

    override fun isWholeNumber(): Boolean = numerator % denominator == BigInteger.ZERO

    override fun toIntegral(): BigInteger = numerator / denominator

    override fun toDouble(): Double = numerator.toDouble() / denominator.toDouble()

    override fun sign(): Int = numerator.signum() * denominator.signum()

    override fun equals(other: Any?): Boolean =
        other is BigIntegerFraction && compareTo(other) == 0
}