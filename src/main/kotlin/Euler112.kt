fun main() {
    val (solution, _) = generateSequence(Pair(1, 0), { (i, nrBouncy) -> Pair(i + 1, nrBouncy + if (isBouncy(i + 1)) 1 else 0)}).find {
        it.second > 0 && it.second * 100 / it.first == 99
    }!!

    println ("Solution: $solution")
}

fun isBouncy(nr: Int): Boolean {
    val ds = toDigits(nr)
    var direction = 0
    var turnFound = false
    var i = 1
    while (i < ds.size && !turnFound) {
        if (ds[i] > ds[i-1]) {
            if (direction == 0) {
                direction = 1
            } else if (direction == -1) {
                turnFound = true
            }
        } else
            if (ds[i] < ds[i-1]) {
                if (direction == 0) {
                    direction = -1
                } else if (direction == 1) {
                    turnFound = true
                }
            }
        i++
    }

    return turnFound
}