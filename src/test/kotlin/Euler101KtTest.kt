import Fraction.Companion.fraction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Euler101KtTest {
    @Test
    fun solveEquations_shouldSolveEquations() {
        // x + y     = 1
        //     y + z = 2
        // x + z     = 3
        // Solution: y = 0, x = 1, z = 2

        var equations = arrayOf(
            arrayOf(fraction(1), fraction(1), fraction(0), fraction(1)),
            arrayOf(fraction(0), fraction(1), fraction(1), fraction(2)),
            arrayOf(fraction(1), fraction(0), fraction(1), fraction(3)))

        var solution = solveEquations(equations)
        
        assertArrayEquals(arrayOf(fraction(1), fraction(0), fraction(2)), solution, "Actual: ${solution.joinToString(",")}")

        // x + 2y + 3z  = 1
        // x + y - z = 2
        // -y + z     = -1
        // Solution: y = 0.6, x = 1, z = -0.4

        equations = arrayOf(
            arrayOf(fraction(1), fraction(2), fraction(3), fraction(1)),
            arrayOf(fraction(1), fraction(1), fraction(-1), fraction(2)),
            arrayOf(fraction(0), fraction(-1), fraction(1), fraction(-1)))

        solution = solveEquations(equations)

        assertArrayEquals(arrayOf(fraction(1), fraction(6, 10), fraction(-4, 10)), solution, "Actual: ${solution.joinToString(",")}")
    }
}