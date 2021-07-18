package net.sr89.lizard.math

import net.sr89.lizard.data.Matrix
import net.sr89.lizard.data.Tuple
import net.sr89.lizard.data.cross
import net.sr89.lizard.data.minus
import net.sr89.lizard.data.normalize
import net.sr89.lizard.data.size

/**
 * @param [from] Point: The eye position
 * @param [to] Point: The point where we want to look
 * @param [up] Vector: Indicates which direction is up
 */
fun viewTransform(from: Tuple, to: Tuple, up: Tuple): Matrix {
    val forward = (to - from).normalize()
    val upn = up.normalize()
    val left = forward.cross(upn)
    val trueUp = left.cross(forward)
    val orientation = Matrix.create(
        size(4, 4),
        left.x, left.y, left.z, 0,
        trueUp.x, trueUp.y, trueUp.z, 0,
        -forward.x, -forward.y, -forward.z, 0,
        0, 0, 0, 1
    )
    return orientation * translation(-from.x, -from.y, -from.z)
}