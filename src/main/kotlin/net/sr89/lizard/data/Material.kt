package net.sr89.lizard.data

fun material(): Material = Material(
    null,
    color(1, 1, 1),
    0.1,
    0.9,
    0.9,
    200.0
)

data class Material(
    val pattern: Pattern?,
    val surfaceColor: Color,
    val ambient: Double,
    val diffuse: Double,
    val specular: Double,
    val shininess: Double
)