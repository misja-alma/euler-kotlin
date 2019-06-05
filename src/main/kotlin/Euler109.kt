fun main() {
    abstract class Throw {
        abstract fun score(): Int
        abstract fun type(): Int
    }
    class Singlet(val field: Int) : Throw() {
        override fun score(): Int = field
        override fun type(): Int = 0
    }
    class Doublet(val field: Int) : Throw() {
        override fun score(): Int = field * 2
        override fun type(): Int = 1
    }
    class Triplet(val field: Int) : Throw() {
        override fun score(): Int = field * 3
        override fun type(): Int = 2
    }

    // ordering for 2nd and 3rd throw; triples > doubles > singles; with same type highest wins
    fun isDuplicate(ls: List<Throw>): Boolean {
        val t3 = ls[2]
        val t2 = ls[1]
        return if (t3.type() == t2.type()) t3.score() > t2.score() else t3.type() > t2.type()
    }

    val oneDartFinishes: List<List<Throw>> = (1..20).map { listOf(Doublet(it)) } + listOf(listOf(Doublet(25)))
    val twoDartFinishes: List<List<Throw>> = oneDartFinishes.flatMap { t1 ->
        (1..20).flatMap { t2 ->
            listOf(t1 + Singlet(t2), t1 + Doublet(t2), t1 + Triplet(t2))
        } +
                listOf(t1 + Singlet(25), t1 + Doublet(25))
    }
    val threeDartFinishes: List<List<Throw>> = twoDartFinishes.flatMap { t1 ->
        (1..20).flatMap { t2 ->
            listOf(t1 + Singlet(t2), t1 + Doublet(t2), t1 + Triplet(t2)).filterNot { isDuplicate(it) }
        } +
                listOf(t1 + Singlet(25), t1 + Doublet(25)).filterNot { isDuplicate(it) }
    }

    val solution = (oneDartFinishes + twoDartFinishes + threeDartFinishes).count { it.map { t -> t.score() }.sum() < 100 }

    println ("Solution: $solution")
}
