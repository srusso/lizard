package net.sr89.lizard.data

import net.sr89.lizard.math.inverse
import net.sr89.lizard.math.times

fun position(ray: Ray, t: Number): Tuple {
    val totalDistance = ray.direction * t.toDouble()

    return ray.origin + totalDistance
}

fun ray(origin: Tuple, direction: Tuple): Ray {
    return if (!origin.isPoint() || !direction.isVector()) {
        throw IllegalArgumentException("Origin of ray needs to be a point, while direction needs to be a vector")
    } else {
        Ray(origin, direction)
    }
}

/**
 * [x] and [y] indicate a pixel on the canvas
 */
fun rayForPixel(camera: Camera, x: Number, y: Number): Ray {
    val px = x.toDouble()
    val py = y.toDouble()
    val xoffset = (px + 0.5) * camera.pixelSize
    val yoffset = (py + 0.5) * camera.pixelSize
    val worldx = camera.halfWidth - xoffset
    val worldy = camera.halfHeight - yoffset
    val pixel = camera.transform.inverse() * point(worldx, worldy, -1)
    val origin = camera.transform.inverse() * point(0, 0, 0)
    val direction = (pixel - origin).normalize()
    return ray(origin, direction)
}

data class Ray(val origin: Tuple, val direction: Tuple)