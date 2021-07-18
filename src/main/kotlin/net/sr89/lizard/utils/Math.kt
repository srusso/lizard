package net.sr89.lizard.utils

import kotlin.math.abs

const val DELTA: Double = 0.00001

fun Double.eequals(other: Double): Boolean = abs(this - other) < DELTA