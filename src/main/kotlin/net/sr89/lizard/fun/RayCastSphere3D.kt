package net.sr89.lizard.`fun`

import net.sr89.lizard.data.canvas
import net.sr89.lizard.data.color
import net.sr89.lizard.data.hit
import net.sr89.lizard.data.material
import net.sr89.lizard.data.minus
import net.sr89.lizard.data.normalize
import net.sr89.lizard.data.point
import net.sr89.lizard.data.pointLight
import net.sr89.lizard.data.position
import net.sr89.lizard.data.ray
import net.sr89.lizard.data.unaryMinus
import net.sr89.lizard.math.intersect
import net.sr89.lizard.math.lighting
import net.sr89.lizard.math.translation
import net.sr89.lizard.objects.normalAt
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
        .withMaterial(material().copy(surfaceColor = color(1, 0.0, 0)))
        .thenTransform(translation(0, 0, 5))
    val light = pointLight(
        point(-10, 10, -10),
        color(1, 1, 1)
    )

    for (x in 0..canvas.width) {
        for (y in 0..canvas.height) {
            val worldY = half - pixelSize * y
            val worldX = -half + pixelSize * x

            val rayWallIntersection = point(worldX, worldY, wallZ)

            val ray = ray(rayOrigin, (rayWallIntersection - rayOrigin).normalize())
            val intersections = ray.intersect(sphere)

            if (intersections.size > 0) {
                val hit = intersections.hit()!!
                val hitPoint = position(ray, hit.t)
                val normal = hit.obj.normalAt(hitPoint)
                val eye = -ray.direction
                val color = lighting(hit.obj.material(), hit.obj, light, hitPoint, eye, normal)
                canvas.writePixel(x, y, color)
            }
        }
    }

    canvas.toPPMFormat().writeTo("./spherecasting.ppm")
}