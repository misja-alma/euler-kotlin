import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

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
}