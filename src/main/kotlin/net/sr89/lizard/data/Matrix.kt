package net.sr89.lizard.data

import net.sr89.lizard.utils.eequals
import net.sr89.lizard.utils.toFlattenedBidimensionalArray

class Matrix private constructor(private val size: Size2D, private val values: Array<Component>) {

    fun rows(): Int = size.rows

    fun columns(): Int = size.columns

    fun size(): Size2D = size

    private fun index(row: Int, column: Int): Int {
        val idx = row * columns() + column

        if (idx < 0 || idx >= values.size) {
            throw IllegalArgumentException("Cannot access element at ($row, $column) in matrix of size (${rows()}, ${columns()})")
        }

        return idx
    }

    fun get(row: Int, column: Int): Component {
        return values[index(row, column)]
    }

    fun map(transformation: (row: Int, column: Int, value: Component) -> Component): Matrix {
        val values = Array(rows() * columns()) { 0.0 }

        size.forEach { r, c, _ ->
            values[index(r, c)] = transformation(r, c, get(r, c))
        }

        return create(size, *values)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (size != other.size) return false

        for (i in values.indices) {
            if (!(values[i].eequals(other.values[i]))) {
                return false
            }
        }

        return true
    }

    override fun hashCode(): Int {
        var result = size.hashCode()
        result = 31 * result + values.contentHashCode()
        return result
    }

    override fun toString(): String {
        val b = StringBuilder()

        for (r in 0 until rows()) {
            for (c in 0 until columns()) {
                b.append("%.5f".format(get(r, c))).append(' ')
            }
            b.appendLine()
        }

        return b.toString()
    }


    companion object {
        fun create(size: Size2D, vararg values: Number): Matrix {
            return Matrix(size, toFlattenedBidimensionalArray(size, Number::toDouble, *values))
        }

        fun identity(size: Int): Matrix {
            return Matrix(
                size(size, size),
                Array(size * size) {
                    if (it % (size + 1) == 0) 1.0 else 0.0
                }
            )
        }
    }
}