import java.util.*

fun main() {
    // Prim's algo for building a minimum spanning tree:
    // Start with a random vertex. Add all its outgoing edges to the pq.
    // Then take the cheapest edge not connected to the result graph yet (using a priority queue).
    // Add the resulting vertex to the result and all its outgoing edges to the pq.
    // Stop when all edges are in the result.
    // The weight saved is the connection matrix's sum / 2 minus the sum of the weight of the result graph.

    val input = ClassLoader.getSystemResource("p107_network.txt").readText()

    val matrix: List<List<Int>> = input.lines().map {
        it.split(",").map { w ->
            when (w) {
                "-" -> 0
                else -> w.toInt()
            }
        }
    }

    class Edge(val weight: Int, val target: Int)

    val edgeComparator = Comparator<Edge> { a, b ->
        a.weight.compareTo(b.weight)
    }

    fun edgesFrom(from: Int): List<Edge> = matrix[from].indices.filter { matrix[from][it] != 0 }.map { Edge(matrix[from][it], it) }

    val minSpanningTree = mutableSetOf<Edge>()
    val usedVertices = mutableSetOf<Int>()
    val pq = PriorityQueue<Edge>(edgeComparator)

    pq.addAll(edgesFrom(0))
    usedVertices += 0

    while (usedVertices.size < matrix.size) {
        val nextEdge = pq.remove()
        if (!usedVertices.contains(nextEdge.target)) {
            minSpanningTree += nextEdge
            usedVertices += nextEdge.target
            pq.addAll(edgesFrom(nextEdge.target))
        }
    }

    val originalWeight = matrix.map { it.sum() }.sum() / 2
    val newWeight = minSpanningTree.sumBy { it.weight }

    println("Solution: ${originalWeight - newWeight}")
}
