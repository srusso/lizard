package net.sr89.lizard.data

fun pointLight(position: Tuple, intensity: Color) = PointLight(position, intensity)

data class PointLight(val position: Tuple, val intensity: Color)
