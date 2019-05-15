import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Euler99KtTest {
    @Test
    fun comparePowers_shouldComparePowers() {
        // 2^3 > 4^1
        assertTrue(comparePowers(2, 3, 4, 1) > 0)
        // 4^1 < 2^3
        assertTrue(comparePowers(4, 1, 2, 3) < 0)
    }
}