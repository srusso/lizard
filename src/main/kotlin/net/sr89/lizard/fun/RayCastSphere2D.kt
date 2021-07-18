package net.sr89.lizard.`fun`

import net.sr89.lizard.data.canvas
import net.sr89.lizard.data.minus
import net.sr89.lizard.data.normalize
import net.sr89.lizard.data.point
import net.sr89.lizard.data.ray
import net.sr89.lizard.data.redColor
import net.sr89.lizard.math.intersect
import net.sr89.lizard.math.rotationZ
import net.sr89.lizard.math.scaling
import net.sr89.lizard.math.translation
import net.sr89.lizard.objects.sphere
import net.sr89.lizard.utils.toPPMFormat
import net.sr89.lizard.utils.writeTo

fun main() {
    val wallZ = 10.0
    val wallSize = 7.0
    val rayOrigin = point(0, 0, -5)
    val canvas = canvas(200, 200)
    val pixelSize = wallSize / canvas.height
    val half = wallSize / 2
    val sphere = sphere()
        .thenTransform(scaling(1, 0.5, 1))
        .thenTransform(rotationZ(Math.PI / 4))
        .thenTransform(translation(0.2, 0.2, 0))

    for (x in 0..canvas.width) {
        for (y in 0..canvas.height) {
            val worldY = half - pixelSize * y
            val worldX = -half + pixelSize * x

            val rayWallIntersection = point(worldX, worldY, wallZ)

            val ray = ray(rayOrigin, (rayWallIntersection - rayOrigin).normalize())

            if (ray.intersect(sphere).size > 0) {
                canvas.writePixel(x, y, redColor)
            }
        }
    }

    canvas.toPPMFormat().writeTo("./spherecasting.ppm")
}