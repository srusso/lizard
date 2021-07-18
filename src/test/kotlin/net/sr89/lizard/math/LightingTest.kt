package net.sr89.lizard.math

import net.sr89.lizard.data.color
import net.sr89.lizard.data.material
import net.sr89.lizard.data.point
import net.sr89.lizard.data.pointLight
import net.sr89.lizard.data.vector
import net.sr89.lizard.objects.sphere
import net.sr89.lizard.world
import org.junit.jupiter.api.Test
import kotlin.math.sqrt
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LightingTest {
    @Test
    internal fun eyeLookingNormallyAtWallWithLightBehindEye() {
        val m = material()
        val position = point(0, 0, 0)
        val eye = vector(0, 0, -1)
        val normalv = vector(0, 0, -1)
        val light = pointLight(point(0, 0, -10), color(1, 1, 1))
        val result = lighting(m, sphere(), light, position, eye, normalv)

        assertEquals(color(1.9, 1.9, 1.9), result)
    }

    @Test
    internal fun `in shadow`() {
        val m = material()
        val position = point(0, 0, 0)
        val eye = vector(0, 0, -1)
        val normalv = vector(0, 0, -1)
        val light = pointLight(point(0, 0, -10), color(1, 1, 1))
        val result = lighting(m, sphere(), light, position, eye, normalv, true)

        assertEquals(color(0.1, 0.1, 0.1), result)
    }

    @Test
    internal fun eyeBetweenLightAndSurfaceButAt45Degrees() {
        val m = material()
        val position = point(0, 0, 0)
        val eye = vector(0, sqrt(2.0) / 2, -sqrt(2.0) / 2)
        val normalv = vector(0, 0, -1)
        val light = pointLight(point(0, 0, -10), color(1, 1, 1))
        val result = lighting(m, sphere(), light, position, eye, normalv)

        assertEquals(color(1.0, 1.0, 1.0), result)
    }

    @Test
    internal fun lightOffset45Degrees() {
        val m = material()
        val position = point(0, 0, 0)
        val eye = vector(0, 0, -1)
        val normalv = vector(0, 0, -1)
        val light = pointLight(point(0, 10, -10), color(1, 1, 1))
        val result = lighting(m, sphere(), light, position, eye, normalv)

        assertEquals(color(0.7364, 0.7364, 0.7364), result)
    }

    @Test
    internal fun eyeInReflectionPath() {
        val m = material()
        val position = point(0, 0, 0)
        val eye = vector(0, -sqrt(2.0) / 2, -sqrt(2.0) / 2)
        val normalv = vector(0, 0, -1)
        val light = pointLight(point(0, 10, -10), color(1, 1, 1))
        val result = lighting(m, sphere(), light, position, eye, normalv)

        assertEquals(color(1.6364, 1.6364, 1.6364), result)
    }

    @Test
    internal fun lightBehindWall() {
        val m = material()
        val position = point(0, 0, 0)
        val eye = vector(0, 0, -1)
        val normalv = vector(0, 0, -1)
        val light = pointLight(point(0, 0, 10), color(1, 1, 1))
        val result = lighting(m, sphere(), light, position, eye, normalv)

        assertEquals(color(0.1, 0.1, 0.1), result)
    }

    @Test
    internal fun `no shadow when nothing is collinear with poin and light`() {
        assertFalse(isShadowed(world(), point(0, 10, 0)))
    }

    @Test
    internal fun `shadow when an object is between the point and the light`() {
        assertTrue(isShadowed(world(), point(10, -10, 10)))
    }

    @Test
    internal fun `no shadow when object is behind light`() {
        assertFalse(isShadowed(world(), point(-20, 20, -20)))
    }

    @Test
    internal fun `no shadow when object is behind the point`() {
        assertFalse(isShadowed(world(), point(-2, 2, -2)))
    }
}