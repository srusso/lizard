package net.sr89.lizard.data

import net.sr89.lizard.math.render
import net.sr89.lizard.math.rotationY
import net.sr89.lizard.math.times
import net.sr89.lizard.math.translation
import net.sr89.lizard.math.viewTransform
import net.sr89.lizard.utils.DELTA
import net.sr89.lizard.world
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class CameraTest {
    @Test
    internal fun `pixel size for horizontal canvas`() {
        val camera = camera(200, 125, Math.PI / 2)

        assertEquals(0.01, camera.pixelSize, DELTA)
    }

    @Test
    internal fun `pixel size for vertical canvas`() {
        val camera = camera(125, 200, Math.PI / 2)

        assertEquals(0.01, camera.pixelSize, DELTA)
    }

    @Test
    internal fun `ray through the center of the canvas`() {
        val camera = camera(201, 101, Math.PI / 2)
        val r = rayForPixel(camera, 100, 50)

        assertEquals(point(0, 0, 0), r.origin)
        assertEquals(vector(0, 0, -1), r.direction)
    }

    @Test
    internal fun `ray through canvas corner`() {
        val camera = camera(201, 101, Math.PI / 2)
        val r = rayForPixel(camera, 0, 0)

        assertEquals(point(0, 0, 0), r.origin)
        assertEquals(vector(0.66519, 0.33259, -0.66851), r.direction)
    }

    @Test
    internal fun `ray when camera is transformed`() {
        val camera = camera(
            201,
            101,
            Math.PI / 2,
            rotationY(Math.PI / 4) * translation(0, -2, 5)
        )
        val r = rayForPixel(camera, 100, 50)

        assertEquals(point(0, 2, -5), r.origin)
        assertEquals(vector(sqrt(2.0) / 2, 0, -sqrt(2.0) / 2), r.direction)
    }

    @Test
    internal fun `rendering a world with a camera`() {
        val w = world()
        val from = point(0, 0, -5)
        val to = point(0, 0, 0)
        val up = vector(0, 1, 0)
        val c = camera(11, 11, Math.PI / 2).copy(transform = viewTransform(from, to, up))
        val img: Canvas = render(c, w)
        assertEquals(color(0.38066, 0.47583, 0.2855), img.pixelAt(5, 5))

    }
}