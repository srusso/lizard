package net.sr89.lizard.data

import net.sr89.lizard.utils.eequals
import kotlin.math.sqrt

fun tuple(x: Number, y: Number, z: Number, w: Number) = Tuple(x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble())

fun point(x: Number, y: Number, z: Number) = tuple(x.toDouble(), y.toDouble(), z.toDouble(), 1.0)

fun vector(x: Number, y: Number, z: Number) = tuple(x.toDouble(), y.toDouble(), z.toDouble(), 0.0)

data class Tuple(val x: Component, val y: Component, val z: Component, val w: Component) {

    fun isPoint(): Boolean = w == 1.0
    fun isVector(): Boolean = w == 0.0

    override fun equals(other: Any?): Boolean = when(other) {
        is Tuple -> x.eequals(other.x) && y.eequals(other.y) && z.eequals(other.z) && w.eequals(other.w)
        else -> false
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + w.hashCode()
        return result
    }
}

operator fun Tuple.plus(other: Tuple): Tuple =
    tuple(x + other.x, y + other.y, z + other.z, w + other.w)

operator fun Tuple.minus(other: Tuple): Tuple =
    tuple(x - other.x, y - other.y, z - other.z, w - other.w)

operator fun Tuple.times(scalar: Number): Tuple {
    val multiplier = scalar.toDouble()
    return tuple(x * multiplier, y * multiplier, z * multiplier, w * multiplier)
}

operator fun Tuple.div(scalar: Number): Tuple {
    val divisor = scalar.toDouble()
    return tuple(x / divisor, y / divisor, z / divisor, w / divisor)
}

operator fun Number.times(tuple: Tuple): Tuple = tuple * this

operator fun Tuple.unaryMinus(): Tuple = tuple(-x, -y, -z, -w)

fun Tuple.magnitude(): Double = when {
    isVector() -> sqrt(x * x + y * y + z * z)
    else -> throw ArithmeticException("Can only calculate magnitude of vectors. This tuple is $this")
}

fun Tuple.normalize(): Tuple = when {
    isVector() -> {
        val magnitude = magnitude()

        vector(x / magnitude, y / magnitude, z / magnitude)
    }
    else -> throw ArithmeticException("Can only calculate magnitude of vectors. This tuple is $this")
}

/**
 * Dot product.
 */
operator fun Tuple.times(other: Tuple): Double = when {
    isVector() && other.isVector() -> x * other.x + y * other.y + z * other.z
    else -> throw ArithmeticException("The dot product only makes sense for vectors. This was: $this * $other")
}

fun Tuple.cross(other: Tuple): Tuple = when {
    isVector() && other.isVector() -> vector(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )
    else -> throw ArithmeticException("The cross product only makes sense for vectors. This was: $this * $other")
}

fun Tuple.asColumnMatrix(): Matrix = Matrix.create(size(4,  1), x, y, z, w)

fun Tuple.reflect(normalVector: Tuple): Tuple = this - normalVector * 2 * (this * normalVector)