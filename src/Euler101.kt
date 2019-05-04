import java.lang.IllegalArgumentException

fun main() {
    // Generate results for x = 1, 2 etc
    // For n = 1 .. 9 :
    // Fit a n-polynomial to the first n results and let it generate result n
    // Fitting the polynomial: It is of form aX^(n-1) + bX^n-2 ... + m
    // Then just fill in x every time for results for x = 1 etc.
    // The result is m linear equations with m variables. This can be solved with Gaussian elimination.

}

// The array should have r rows and r + 1 cols so that the last col contains the results
fun solveEquations(coefs: Array<Array<Fraction>>): Array<Fraction> {
    val usedRows = HashSet<Int>()
    val nrCols = coefs[0].size - 1

    (0 until nrCols).forEach { col ->
        // find non zero col value in unused row and add row to used rows
        val r = coefs.indices
            .find { row -> !usedRows.contains(row) && coefs[row][col] != Fraction(0) }
            ?: throw IllegalArgumentException("Redundant column found: $col")
        usedRows.add(r)

        // normalize row so col has value 1
        val colValue = coefs[r][col]
        coefs[r].indices.forEach { c -> coefs[r][c] /= colValue }

        // deduct row from all other non zero rows
        coefs.indices.forEach { row ->
            if (row != r && coefs[row][col] != Fraction(0)) {
                val deductFactor = coefs[row][col]
                coefs[row].indices.forEach { c -> coefs[row][c] -= deductFactor * coefs[r][c] }
            }
        }
    }

    val result = Array(nrCols){ Fraction(0) }
    coefs.indices.forEach { r ->
        val solvedCol =
            (0 until nrCols).find { coefs[r][it] == Fraction(1) } ?: throw IllegalArgumentException("Redundant row found: $r")
        result[solvedCol] = coefs[r][nrCols]
    }

    return result
}