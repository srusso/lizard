package net.sr89.lizard.data

fun size(rows: Int, columns: Int): Size2D = Size2D(rows, columns)

data class Size2D(val rows: Int, val columns: Int) {
    fun forEach(consumer: (Int, Int, Int) -> Unit) {
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                consumer(i, j, i * columns + j)
            }
        }
    }
}