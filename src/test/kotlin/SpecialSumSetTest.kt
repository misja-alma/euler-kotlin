import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class SpecialSumSetTest {
    @Test
    fun isSpecialSumSet_shouldReturnCorrectAnswer() {

        assertFalse(isSpecialSumSet(listOf(81, 88, 75, 42, 87, 84, 86, 65).sorted(), mutableMapOf()))

        assertTrue(isSpecialSumSet(listOf(1).sorted(), mutableMapOf()))
        assertTrue(isSpecialSumSet(listOf(1, 2).sorted(), mutableMapOf()))
        assertTrue(isSpecialSumSet(listOf(2, 3, 4).sorted(), mutableMapOf()))
        assertTrue(isSpecialSumSet(listOf(3, 5, 6, 7).sorted(), mutableMapOf()))
        assertTrue(isSpecialSumSet(listOf(6, 9, 11, 12, 13).sorted(), mutableMapOf()))
        assertTrue(isSpecialSumSet(listOf(11, 17, 20, 22, 23, 24).sorted(), mutableMapOf()))
        assertTrue(isSpecialSumSet(listOf(11, 18, 19, 20, 22, 25).sorted(), mutableMapOf()))
        assertTrue(isSpecialSumSet(listOf(157, 150, 164, 119, 79, 159, 161, 139, 158).sorted(), mutableMapOf()))
    }
}
