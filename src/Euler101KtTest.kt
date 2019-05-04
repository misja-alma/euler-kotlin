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
            arrayOf(Fraction(1), Fraction(1), Fraction(0), Fraction(1)),
            arrayOf(Fraction(0), Fraction(1), Fraction(1), Fraction(2)),
            arrayOf(Fraction(1), Fraction(0), Fraction(1), Fraction(3)))

        var solution = solveEquations(equations)
        
        assertArrayEquals(arrayOf(Fraction(1), Fraction(0), Fraction(2)), solution, "Actual: ${solution.joinToString(",")}")

        // x + 2y + 3z  = 1
        // x + y - z = 2
        // -y + z     = -1
        // Solution: y = 0.6, x = 1, z = -0.4

        equations = arrayOf(
            arrayOf(Fraction(1), Fraction(2), Fraction(3), Fraction(1)),
            arrayOf(Fraction(1), Fraction(1), Fraction(-1), Fraction(2)),
            arrayOf(Fraction(0), Fraction(-1), Fraction(1), Fraction(-1)))

        solution = solveEquations(equations)

        assertArrayEquals(arrayOf(Fraction(1), Fraction(6, 10), Fraction(-4, 10)), solution, "Actual: ${solution.joinToString(",")}")
    }
}