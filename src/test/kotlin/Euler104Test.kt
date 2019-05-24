import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class Euler104KtTest {
    @Test
    fun fibRecursive_shouldReturnFibonaccieNumber() {
        assertEquals(BigInteger.ONE, fibRecursive(1).toBigInteger())
        assertEquals(BigInteger.valueOf(21), fibRecursive(8).toBigInteger())
    }
}
