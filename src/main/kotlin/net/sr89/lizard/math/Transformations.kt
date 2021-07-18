package net.sr89.lizard.math

import net.sr89.lizard.data.Matrix
import net.sr89.lizard.data.Tuple
import net.sr89.lizard.data.size
import kotlin.math.cos
import kotlin.math.sin

fun translation(x: Number, y: Number, z: Number): Matrix =
    Matrix.create(
        size(4, 4),
        1, 0, 0, x.toDouble(),
        0, 1, 0, y.toDouble(),
        0, 0, 1, z.toDouble(),
        0, 0, 0, 1
    )

fun scaling(x: Number, y: Number, z: Number): Matrix =
    Matrix.create(
        size(4, 4),
        x.toDouble(), 0, 0, 0,
        0, y.toDouble(), 0, 0,
        0, 0, z.toDouble(), 0,
        0, 0, 0, 1
    )

fun rotationX(radians: Double): Matrix =
    Matrix.create(
        size(4, 4),
        1, 0, 0, 0,
        0, cos(radians), -sin(radians), 0,
        0, sin(radians), cos(radians), 0,
        0, 0, 0, 1
    )

fun rotationY(radians: Double): Matrix =
    Matrix.create(
        size(4, 4),
        cos(radians), 0, sin(radians), 0,
        0, 1, 0, 0,
        -sin(radians), 0, cos(radians), 0,
        0, 0, 0, 1
    )

fun rotationZ(radians: Double): Matrix =
    Matrix.create(
        size(4, 4),
        cos(radians), -sin(radians), 0, 0,
        sin(radians), cos(radians), 0, 0,
        0, 0, 1, 0,
        0, 0, 0, 1
    )

fun shearing(xy: Number, xz: Number, yx: Number, yz: Number, zx: Number, zy: Number): Matrix =
    Matrix.create(
        size(4, 4),
        1, xy, xz, 0,
        yx, 1, yz, 0,
        zx, zy, 1, 0,
        0, 0, 0, 1
    )

fun Matrix.then(transformation: Matrix): Matrix = transformation * this

fun Matrix.applyTo(tuple: Tuple): Tuple = this * tuple