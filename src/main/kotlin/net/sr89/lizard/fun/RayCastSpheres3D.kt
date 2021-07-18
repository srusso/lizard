package net.sr89.lizard.`fun`

import net.sr89.lizard.data.camera
import net.sr89.lizard.data.color
import net.sr89.lizard.data.material
import net.sr89.lizard.data.point
import net.sr89.lizard.data.pointLight
import net.sr89.lizard.data.stripePattern
import net.sr89.lizard.data.vector
import net.sr89.lizard.data.white
import net.sr89.lizard.math.render
import net.sr89.lizard.math.rotationX
import net.sr89.lizard.math.scaling
import net.sr89.lizard.math.translation
import net.sr89.lizard.math.viewTransform
import net.sr89.lizard.objects.plane
import net.sr89.lizard.objects.sphere
import net.sr89.lizard.utils.toPPMFormat
import net.sr89.lizard.utils.writeTo
import net.sr89.lizard.world
import kotlin.math.PI

fun main() {
    val world = world()
        .copy(light = pointLight(point(-10, 10, -10), color(1, 1, 1)))
        .plusObject(rightSphere())
        .plusObject(floor())
        .plusObject(
            floor()
                .thenTransform(rotationX(PI / 2))
                .thenTransform(translation(0, 0, 5))
        )

    val camera = camera(1000, 1000, Math.PI / 3)
        .copy(
            transform = viewTransform(
                point(0, 1.5, -5),
                point(0, 1, 0),
                vector(0, 1, 0)
            )
        )

    val canvas = render(camera, world)

    canvas.toPPMFormat().writeTo("./spherescasting.ppm")
}

fun rightSphere() = sphere()
    .withMaterial(
        material().copy(
            surfaceColor = color(0.5, 1, 0.1),
            diffuse = 0.7,
            specular = 0.3,
            pattern = stripePattern(white, color(0.8, 0.1, 0.2)).copy(transform = scaling(0.5, 0.5, 0.5))
        )
    )
    .thenTransform(scaling(0.5, 0.5, 0.5))
    .thenTransform(translation(1.5, 0.5, -0.5))

fun floor() = plane()
