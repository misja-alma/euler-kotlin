// Odd powers of n fall away, this means:
// Even power of n:
// 2 (a^n +(n over 2)a^(n-2) + ... 1)
// Odd power of n:
// 2(a^n+(n over 1)a^(n-1) + (next even term for power) + .. (n over n-1)a)

// After div by a^2:
// Even power of n:
// 2 ((n over n-1)a + 1) = 2
// Odd power of n:
// 2((n over n-1)a) = 2na
// Note that the term (n over n-1)a is always there in both cases because we divide by a^2
// highest remainder:

// Even power of n: 2
// Odd power of n: a^2 - a for odd a, a^2 - 2a for all other a

fun main() {
    val solution = (3..1000).map { a -> if (a % 2 == 0) a * (a - 2) else a * (a - 1) }.sum()

    println("Solution: $solution")
}
