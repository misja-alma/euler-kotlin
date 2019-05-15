import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Euler53KtTest {
    @Test
    fun fac_shouldReturnFacultyUntilLowerBound() {
        assertEquals(6, fac(3, 0).toInt())
        assertEquals(3, fac(3, 2).toInt())
        assertEquals(1, fac(3, 3).toInt())
    }

    @Test
    fun nOverM_shouldReturnCorrectResult() {
        assertEquals(10, nOverM(5, 3).toInt())
    }
}