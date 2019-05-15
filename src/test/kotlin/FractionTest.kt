import Fraction.Companion.fraction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FractionTest {
    @Test
    fun plus_shouldReturnTheSumOfFractions() {
        assertEquals(fraction(5, 2), fraction(1, 2) + fraction(2))
        assertEquals(fraction(7, 12), fraction(1, 3) + fraction(1, 4))
        assertEquals(fraction(1), fraction(2, 3) + fraction(2, 6))
    }

    @Test
    fun minus_shouldReturnTheDifferenceOfFractions() {
        assertEquals(fraction(-3, 2), fraction(1, 2) - fraction(2))
    }

    @Test
    fun times_shouldReturnTheProductOfFractions() {
        assertEquals(fraction(8, 15), fraction(2, 3) * fraction(4, 5))
    }

    @Test
    fun div_shouldReturnTheQuotientOfFractions() {
        assertEquals(fraction(10, 12), fraction(2, 3) / fraction(4, 5))
    }

    @Test
    fun simplify_shouldReturnTheSimplifiedfraction() {
        assertEquals(fraction(2, 3), fraction(2, 3).simplify())
        assertEquals(fraction(6, 5), fraction(12, 10).simplify())
        assertEquals(fraction(-1), fraction(-20, 20).simplify())
    }

    @Test
    fun compareTo_shouldCcompareTheFractions() {
        assertEquals(1, fraction(2, 3).compareTo(fraction(1, 3)))
        assertEquals(-1, fraction(2, 3).compareTo(fraction(3, 3)))
        assertEquals(0, fraction(2, 3).compareTo(fraction(4, 6)))
    }
}