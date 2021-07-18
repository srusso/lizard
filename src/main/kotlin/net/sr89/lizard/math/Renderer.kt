package net.sr89.lizard.math

import net.sr89.lizard.World
import net.sr89.lizard.data.Camera
import net.sr89.lizard.data.Canvas
import net.sr89.lizard.data.canvas
import net.sr89.lizard.data.rayForPixel

fun render(camera: Camera, world: World): Canvas {
    val img = canvas(camera.hsize, camera.vsize)

    for (y in 0 until camera.vsize) {
        for (x in 0 until camera.hsize) {
            val ray = rayForPixel(camera, x, y)
            img.writePixel(x, y, colorAt(world, ray))
        }
    }

    return img
}