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

    @Test
    fun isPrime_shouldCorrectlyRecognizePrimeNumbers() {
        val cache = PrimeCache()

        assertTrue(isPrime(2, cache))
        assertFalse(isPrime(1, cache))
        assertFalse(isPrime(4, cache))
        assertFalse(isPrime(25, cache))
        assertTrue(isPrime(11, cache))
        assertFalse(isPrime(12, cache))
        assertFalse(isPrime(21, cache))
        assertTrue(isPrime(37, cache))
    }

    @Test
    fun primes_shouldReturnStreamOfPrimes() {
        val cache = PrimeCache()

        assertEquals(listOf<Long>(2, 3, 5, 7, 11), primes(cache).take(5).toList())
    }

    @Test
    fun primesBySieve_shouldReturnPrimeNumbers() {
        assertEquals(listOf(2, 3, 5, 7, 11, 13, 17, 19), primesBySieve(19))
    }
}