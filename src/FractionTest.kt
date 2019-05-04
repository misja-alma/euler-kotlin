import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FractionTest {
    @Test
    fun plus_shouldReturnTheSumOfFractions() {
        assertEquals(Fraction(5, 2), Fraction(1, 2) + Fraction(2))
        assertEquals(Fraction(7, 12), Fraction(1, 3) + Fraction(1, 4))
        assertEquals(Fraction(1), Fraction(2, 3) + Fraction(2, 6))
    }

    @Test
    fun minus_shouldReturnTheDifferenceOfFractions() {
        assertEquals(Fraction(-3, 2), Fraction(1, 2) - Fraction(2))
    }

    @Test
    fun times_shouldReturnTheProductOfFractions() {
        assertEquals(Fraction(8, 15), Fraction(2, 3) * Fraction(4, 5))
    }

    @Test
    fun div_shouldReturnTheQuotientOfFractions() {
        assertEquals(Fraction(10, 12), Fraction(2, 3) / Fraction(4, 5))
    }

    @Test
    fun simplify_shouldReturnTheSimplifiedFraction() {
        assertEquals(Fraction(2, 3), Fraction(2, 3).simplify())
        assertEquals(Fraction(6, 5), Fraction(12, 10).simplify())
        assertEquals(Fraction(-1), Fraction(-20, 20).simplify())
    }

    @Test
    fun compareTo_shouldCcompareTheFractions() {
        assertEquals(1, Fraction(2, 3).compareTo(Fraction(1, 3)))
        assertEquals(-1, Fraction(2, 3).compareTo(Fraction(3, 3)))
        assertEquals(0, Fraction(2, 3).compareTo(Fraction(4, 6)))
    }
}