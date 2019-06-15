import java.util.*
import kotlin.Comparator

// BFS. Use a pq with ordering 1. min. nr of steps 2. min. distance to target. Finish as soon as target reached.
// We want to keep the set of step sizes used as well as the nr of steps, together with the distance.
// Skip paths that exceed the target. Eliminate duplicate paths.
// Also paths can be pruned for which another path exists with the same distance, smaller or equal nr of steps and whose step set is a superset of the victim.
// This is actually a generalization of eliminating duplicates.
// Note: we could do it even faster by keeping the set of previously computing paths for the next target.

// usedPowers contains all powers used plus the current one
data class PowerPath(val usedPowers: Set<Int>, val nrMults: Int, val currentPower: Int)

val powerPathComparator = Comparator<PowerPath> { p1, p2 ->
    if (p1.nrMults == p2.nrMults) p2.currentPower.compareTo(p1.currentPower) else p1.nrMults.compareTo(p2.nrMults)
}

fun generateNextSteps(p: PowerPath): Set<PowerPath> = p.usedPowers.map {
    val newPower = p.currentPower + it
    PowerPath(p.usedPowers + newPower, p.nrMults + 1, newPower)
}.toSet()

fun leastMultsToTarget(target: Int): PowerPath {
    val pq = PriorityQueue<PowerPath>(powerPathComparator)
    pq.add(PowerPath(setOf(1), 0, 1))
    val usedPowers = mutableMapOf(1 to mutableSetOf(1))

    fun isDominated(p: PowerPath): Boolean {
        val existingPathLengths = usedPowers[p.usedPowers.hashCode()] ?: mutableSetOf()
        return existingPathLengths.contains(p.currentPower)
    }

    while (pq.peek().currentPower != target) {
        val next = pq.remove()
        val nextSteps = generateNextSteps(next)
                .filterNot { it.currentPower > target }
                .filterNot{ isDominated(it) }
        pq.addAll(nextSteps)
        nextSteps.forEach {
            val hash = it.usedPowers.hashCode()
            if (!usedPowers.containsKey(hash)) {
                usedPowers += hash to mutableSetOf(it.currentPower)
            } else usedPowers[hash]!! +=  it.currentPower
        }
    }

    return pq.peek()
}

fun main () {
    val solution = (1 .. 200).map { leastMultsToTarget(it).nrMults }.sum()

    println ("Solution: $solution")
}