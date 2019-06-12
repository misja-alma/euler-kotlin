import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Euler118Test {
    @Test
    fun leftSubLists_shouldReturnAllSubListsContainingLeftElement() {
        assertEquals(listOf(listOf(1), listOf(1, 2), listOf(1, 3), listOf(1, 2, 3)), leftSubLists(listOf(1, 2, 3)))
    }
}
