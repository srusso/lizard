package net.sr89.lizard.data

import kotlin.math.floor

fun stripePattern(a: Color, b: Color): StripePattern = StripePattern(a, b, Matrix.identity(4))

interface Pattern {
    fun colorAt(point: Tuple): Color
    fun transform(): Matrix
}

data class StripePattern(
    private val a: Color,
    private val b: Color,
    private val transform: Matrix
) : Pattern {
    override fun colorAt(point: Tuple): Color {
        return if (floor(point.x).toInt() % 2 == 0) {
            a
        } else {
            b
        }
    }

    override fun transform() = transform
}