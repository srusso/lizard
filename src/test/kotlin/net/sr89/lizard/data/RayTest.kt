package net.sr89.lizard.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RayTest {
    @Test
    internal fun createRay() {
        val origin = point(1, 2, 3)
        val direction = vector(4, 5, 6)

        val ray = ray(origin, direction)

        assertEquals(origin, ray.origin)
        assertEquals(direction, ray.direction)
    }

    @Test
    internal fun positionAlongRay() {
        val ray = ray(point(2, 3, 4), vector(1, 0, 0))

        assertEquals(point(2, 3, 4), position(ray, 0))
        assertEquals(point(3, 3, 4), position(ray, 1))
        assertEquals(point(1, 3, 4), position(ray, -1))
        assertEquals(point(4.5, 3, 4), position(ray, 2.5))
    }
}