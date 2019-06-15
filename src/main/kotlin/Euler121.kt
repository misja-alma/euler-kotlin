import Fraction.Companion.fraction
import java.math.BigInteger

fun main() {
    val pWin = winChance(1, fraction(BigInteger.ONE), 0)

    val payout = pWin.denominator / pWin.numerator

    println ("Solution: $payout")
}

const val nrRounds = 15

fun winChance(round: Int, chanceSoFar: Fraction<BigInteger>, nrBlues: Int): Fraction<BigInteger> =
    if (nrBlues > nrRounds / 2) chanceSoFar else if (round > nrRounds || nrBlues + nrRounds - round < nrRounds / 2) fraction(BigInteger.ZERO) else {
        val nrDiscs = round.toLong() + 1
        val winAfterBlue = winChance(round + 1, chanceSoFar * fraction(BigInteger.ONE, BigInteger.valueOf(nrDiscs)), nrBlues + 1)
        val winAfterRed = winChance(round + 1, chanceSoFar * fraction(BigInteger.valueOf(nrDiscs - 1), BigInteger.valueOf(nrDiscs)), nrBlues)
        winAfterBlue + winAfterRed
    }