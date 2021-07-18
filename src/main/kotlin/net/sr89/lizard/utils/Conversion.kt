package net.sr89.lizard.utils

import net.sr89.lizard.data.Size2D

inline fun <reified T, reified R> toFlattenedBidimensionalArray(
    size: Size2D,
    transform: (T) -> R,
    vararg values: T
): Array<R> {
    if (size.rows < 1 || size.columns < 1) {
        throw IllegalArgumentException("Matrix sizes muse be positive")
    }

    val expectedValues = size.rows * size.columns

    if (expectedValues != values.size) {
        throw IllegalArgumentException("A matrix of size [${size.rows} x ${size.columns}] needs $expectedValues values, got ${values.size}")
    }

    return Array(values.size) { transform(values[it]) }
}