import Fraction.Companion.fraction
import java.lang.IllegalArgumentException
import java.math.BigInteger

fun main() {
    // Generate results for x = 1, 2 etc
    // For n = 1 .. 9 :
    // Fit a n-polynomial to the first n results and let it generate result n. Note that the coefficients are every time the same powers of x for each value of n but every time one extra.
    // Fitting the polynomial: It is of form aX^(n-1) + bX^n-2 ... + m
    // Then just fill in x every time for results for x = 1 etc.
    // The result is m linear equations with m variables. This can be solved with Gaussian elimination.

    val polynomialOrder = 10L

    val results = (1L..polynomialOrder).map { un(it) }
    val allFits = (1..polynomialOrder.toInt()).map { c ->
        val eqs = generateCoefficientEquationsOfPower(c - 1, results)
        val solvedCoefficients = solveEquations(eqs)
        generatePolynomialValueFor(solvedCoefficients, c + 1)
    }

    val solution = allFits.fold (fraction(BigInteger.ZERO, BigInteger.ONE), { total, next -> total + next })
    println("Solution: ${solution.simplify()}")
}

fun generateCoefficientEquationsOfPower(power: Int, results: List<Long>): Array<Array<Fraction<BigInteger>>> =
    // generate power + 1 equations of power + 1 length + 1 (for the result) where each coeff is the power of x
    (0..power).map { p ->
        val row = (0 .. power + 1).map { pw -> fraction(BigInteger.valueOf(pow(p.toLong() + 1, pw))) }.toTypedArray()
        row[power + 1] = fraction(BigInteger.valueOf(results[p]))
        row
    }.toTypedArray()


// Coefs are left to right from power 0 upwards
fun generatePolynomialValueFor(coefs: Array<Fraction<BigInteger>>, input: Int): Fraction<BigInteger> =
    (0 until coefs.size).map { p -> fraction(BigInteger.valueOf(pow(input.toLong(), p))) * coefs[p] }.fold(
        fraction(BigInteger.ZERO),
        { total, next -> total + next })

fun pow(x: Long, to: Int): Long = if (to == 0) 1L else x * pow(x, to - 1)

fun un(n: Long): Long =
    1L - n + pow(n, 2) - pow(n, 3) + pow(n, 4) - pow(n, 5) + pow(n, 6) - pow(n, 7) + pow(n, 8) - pow(n, 9) + pow(n, 10)

// The array should have r rows and r + 1 cols so that the last col contains the results
fun<T> solveEquations(coefs: Array<Array<Fraction<T>>>): Array<Fraction<T>> {
    val usedRows = HashSet<Int>()
    val nrCols = coefs[0].size - 1

    (0 until nrCols).forEach { col ->
        // find non zero col value in unused row and add row to used rows
        val r = coefs.indices.find { row -> !usedRows.contains(row) && coefs[row][col].sign() != 0 }
        if (r == null) {
            println("Warn: redundant column found: $col")
        } else {
            usedRows.add(r)

            // normalize row so col has value 1
            val colValue = coefs[r][col]
            coefs[r].indices.forEach { c -> coefs[r][c] /= colValue }

            // deduct row from all other non zero rows
            coefs.indices.forEach { row ->
                if (row != r && coefs[row][col].sign() != 0) {
                    val deductFactor = coefs[row][col]
                    coefs[row].indices.forEach { c -> coefs[row][c] -= deductFactor * coefs[r][c] }
                }
            }
        }
    }

    val zero = coefs[0][0].zero() // TODO ugly but don't know of any way to have a static zero() method per Fraction implementation
    val result = Array(nrCols) { zero }
    coefs.indices.forEach { r ->
        val solvedCol =
            (0 until nrCols).find { coefs[r][it].isOne() }
                ?: throw IllegalArgumentException("Redundant row found: $r")
        result[solvedCol] = coefs[r][nrCols]
    }

    return result
}