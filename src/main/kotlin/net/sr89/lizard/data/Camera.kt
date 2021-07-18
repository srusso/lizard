package net.sr89.lizard.data

import kotlin.math.tan

fun camera(hsize: Int,
           vsize: Int,
           FOV: Number): Camera =
    Camera(hsize, vsize, FOV.toDouble(), Matrix.identity(4))

fun camera(hsize: Int,
           vsize: Int,
           FOV: Number,
           transform: Matrix): Camera =
    Camera(hsize, vsize, FOV.toDouble(), transform)

data class Camera(
    val hsize: Int,
    val vsize: Int,
    val FOV: Double,
    val transform: Matrix
) {
    private val halfView: Double = tan(FOV / 2)
    private val aspect: Double = hsize.toDouble() / vsize.toDouble()
    val halfWidth: Double = calcHalfWidth()
    val halfHeight: Double = calcHalfHeight()
    val pixelSize: Double = (halfWidth * 2) / hsize

    private fun calcHalfWidth() =
        if (aspect >= 1) {
            halfView
        } else {
            halfView * aspect
        }

    private fun calcHalfHeight() =
        if (aspect >= 1) {
            halfView / aspect
        } else {
            halfView
        }
}
