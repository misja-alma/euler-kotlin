import kotlin.math.sign

class Fraction(val numerator: Long, val denominator: Long = 1L): Comparable<Fraction> {

    operator fun plus(f2: Fraction): Fraction =
        Fraction(numerator * f2.denominator + f2.numerator * denominator, f2.denominator * denominator)

    operator fun minus(f2: Fraction): Fraction =
        Fraction(numerator * f2.denominator - f2.numerator * denominator, f2.denominator * denominator)

    operator fun times(f2: Fraction): Fraction =
        Fraction(numerator * f2.numerator, f2.denominator * denominator)

    operator fun div(f2: Fraction): Fraction =
        Fraction(numerator * f2.denominator, f2.numerator * denominator)

    fun simplify(): Fraction {
        val divider = gcd(numerator, denominator)
        return Fraction(numerator / divider, denominator / divider)
    }

    fun isWholeNumber(): Boolean = numerator % denominator == 0L

    fun toIntegral(): Long = numerator / denominator

    fun toDouble(): Double = numerator.toDouble() / denominator.toDouble()

    // Note that hashcode does not need to be overridden because its contract with equals still holds
    override fun equals(other: Any?): Boolean =
        other is Fraction && compareTo(other) == 0

    override fun compareTo(other: Fraction): Int {
        val dif = this - other
        return dif.numerator.sign
    }
}