import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class UtilsKtTest {
    @Test
    fun gcd_shouldReturnTheGreatestCommonDivisor() {
        assertEquals(2, gcd(6, 4))
        assertEquals(2, gcd(6, 2))
        assertEquals(1, gcd(6, 1))
        assertEquals(4, gcd(128, 4))
        assertEquals(3, gcd(12, 9))
        assertEquals(1, gcd(13, 5))
        assertEquals(2, gcd(-6, 2))
    }

    @Test
    fun sqrt_shouldReturnSquareRootWithinErrorMargin() {
        assertEquals(Math.sqrt(5.0), sqrt(BigDecimal(5), 7).toDouble(), 0.000001)
    }
}