import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

fun gcd(x: Long, y: Long): Long {

    fun doGcd(big: Long, small: Long): Long {
        val remainder = big % small
        return if (remainder == 0L) small else doGcd(small, remainder)
    }

    val px = if (x < 0) -x else x
    val py = if (y < 0) -y else y

    return if (px > py) doGcd(px, py) else doGcd(py, px)
}

fun gcd(x: BigInteger, y: BigInteger): BigInteger {

    fun doGcd(big: BigInteger, small: BigInteger): BigInteger {
        val remainder = big % small
        return if (remainder == BigInteger.ZERO) small else doGcd(small, remainder)
    }

    val px = if (x.signum() < 0) -x else x
    val py = if (y.signum() < 0) -y else y

    return if (px > py) doGcd(px, py) else doGcd(py, px)
}

fun sqrt(x: BigDecimal, nrDigits: Int): BigDecimal {
    val mathContext = MathContext(nrDigits)
    val errorMargin = BigDecimal(1).divide(BigDecimal(10).pow(nrDigits), mathContext)
    var xn = x.divide(BigDecimal(2), mathContext)
    var prev: BigDecimal
    do {
        prev = xn
        xn = (xn + x.divide(xn, mathContext)).divide(BigDecimal(2), mathContext)
    } while ((prev - xn).abs() > errorMargin)

    return xn
}