package net.sr89.lizard.math

import net.sr89.lizard.data.Component
import net.sr89.lizard.data.Matrix
import net.sr89.lizard.data.Tuple
import net.sr89.lizard.data.asColumnMatrix
import net.sr89.lizard.data.size
import net.sr89.lizard.data.tuple
import net.sr89.lizard.utils.eequals

operator fun Matrix.times(other: Matrix): Matrix {
    if (columns() != other.rows()) {
        throw IllegalArgumentException("Cannot multiply matrix with ${columns()} columns with matrix with ${other.rows()} rows")
    }

    val resultSize = size(rows(), other.columns())
    val matrixValues = Array(resultSize.rows * resultSize.columns) { 0.0 }

    resultSize.forEach { r, c, index ->
        matrixValues[index] = rowTimesColumn(this, other, r, c)
    }

    return Matrix.create(resultSize, *matrixValues)
}

operator fun Matrix.times(t: Tuple): Tuple {
    val resultMatrix = this * t.asColumnMatrix()

    return tuple(
        resultMatrix.get(0, 0),
        resultMatrix.get(1, 0),
        resultMatrix.get(2, 0),
        resultMatrix.get(3, 0)
    )
}

fun Matrix.transpose(): Matrix {
    val resultSize = size(columns(), rows())
    val matrixValues = Array(resultSize.rows * resultSize.columns) { 0.0 }

    resultSize.forEach { r, c, index ->
        matrixValues[index] = get(c, r)
    }

    return Matrix.create(resultSize, *matrixValues)
}

private fun rowTimesColumn(a: Matrix, b: Matrix, r: Int, c: Int): Component =
    (0 until a.columns()).sumOf { a.get(r, it) * b.get(it, c) }

fun Matrix.determinant(): Component {
    return if (size(2, 2) == size()) {
        get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0)
    } else {
        (0 until columns()).sumOf { column -> get(0, column) * cofactor(0, column) }
    }
}

fun Matrix.subMatrix(rowToRemove: Int, colToRemove: Int): Matrix {
    val subMatrixSize = size(rows() - 1, columns() - 1)
    val subMatrixValues = Array(subMatrixSize.rows * subMatrixSize.columns) { 0.0 }

    var index = 0

    size().forEach { r, c, _ ->
        if (r != rowToRemove && c != colToRemove) {
            subMatrixValues[index++] = get(r, c)
        }
    }

    return Matrix.create(
        subMatrixSize,
        *subMatrixValues
    )
}

fun Matrix.minor(row: Int, column: Int): Component = subMatrix(row, column).determinant()

fun Matrix.cofactor(row: Int, column: Int): Component {
    val minor = minor(row, column)

    return if ((row + column) % 2 == 0) {
        minor
    } else {
        -minor
    }
}

fun Matrix.invertible(): Boolean = !determinant().eequals(0.0)

fun Matrix.inverse(): Matrix {
    val det = determinant()

    return map { row, column, _ -> cofactor(column, row) / det }
}
