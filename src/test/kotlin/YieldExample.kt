fun naturals(): Sequence<Int> = sequence {
    var i = 0
    while (true) {
        yield((0 .. i).sum())
    }
}

fun main() {
    for (x in naturals().take(10)) {
        println(x)
    }
}