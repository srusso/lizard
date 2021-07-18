package net.sr89.lizard.data

import net.sr89.lizard.utils.eequals

val black = color(0, 0, 0)
val white = color(1, 1, 1)
val redColor = color(1, 0, 0)

fun color(red: Number, green: Number, blue: Number): Color =
    Color(red.toDouble(), green.toDouble(), blue.toDouble())

data class Color(val red: Double, val green: Double, val blue: Double) {
    override fun equals(other: Any?): Boolean = when(other) {
        is Color -> red.eequals(other.red) && green.eequals(other.green) && blue.eequals(other.blue)
        else -> false
    }

    override fun hashCode(): Int {
        var result = red.hashCode()
        result = 31 * result + green.hashCode()
        result = 31 * result + blue.hashCode()
        return result
    }
}

operator fun Color.plus(other: Color): Color =
    color(red + other.red, green + other.green, blue + other.blue)

operator fun Color.minus(other: Color): Color =
    color(red - other.red, green - other.green, blue - other.blue)

operator fun Color.times(scalar: Number): Color {
    val multiplier = scalar.toDouble()
    return color(red * multiplier, green * multiplier, blue * multiplier)
}

operator fun Color.times(other: Color): Color =
    color(red * other.red, green * other.green, blue * other.blue)