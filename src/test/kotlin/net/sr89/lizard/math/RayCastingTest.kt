package net.sr89.lizard.math

import net.sr89.lizard.data.get
import net.sr89.lizard.data.point
import net.sr89.lizard.data.ray
import net.sr89.lizard.data.vector
import net.sr89.lizard.objects.sphere
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

internal class RayCastingTest {
    @Test
    internal fun intersectRayAndSphere() {
        val ray = ray(point(0, 0, -5), vector(0, 0, 1))
        val sphere = sphere()

        val intersectionPoints = ray.intersect(sphere)

        assertEquals(2, intersectionPoints.size)
        assertEquals(4.0, intersectionPoints[0].t)
        assertEquals(6.0, intersectionPoints[1].t)
    }

    @Test
    internal fun intersectSphereTangentially() {
        val ray = ray(point(0, 1, -5), vector(0, 0, 1))
        val sphere = sphere()

        val intersectionPoints = ray.intersect(sphere)

        assertEquals(2, intersectionPoints.size)
        assertEquals(5.0, intersectionPoints[0].t)
        assertEquals(5.0, intersectionPoints[1].t)
    }

    @Test
    internal fun rayDoesNotIntersectSphere() {
        val ray = ray(point(0, 2, -5), vector(0, 0, 1))
        val sphere = sphere()

        val intersectionPoints = ray.intersect(sphere)

        assertEquals(0, intersectionPoints.size)
    }

    @Test
    internal fun rayOriginatesInsideSphere() {
        val ray = ray(point(0, 0, 0), vector(0, 0, 1))
        val sphere = sphere()

        val intersectionPoints = ray.intersect(sphere)

        assertEquals(2, intersectionPoints.size)
        assertEquals(-1.0, intersectionPoints[0].t)
        assertEquals(1.0, intersectionPoints[1].t)
    }

    @Test
    internal fun rayOriginatesAfterSphere() {
        val ray = ray(point(0, 0, 5), vector(0, 0, 1))
        val sphere = sphere()

        val intersectionPoints = ray.intersect(sphere)

        assertEquals(2, intersectionPoints.size)
        assertEquals(-6.0, intersectionPoints[0].t)
        assertEquals(-4.0, intersectionPoints[1].t)
    }

    @Test
    internal fun intersectedObject() {
        val ray = ray(point(0, 0, -5), vector(0, 0, 1))
        val sphere = sphere()

        val intersectionPoints = ray.intersect(sphere)

        assertEquals(2, intersectionPoints.size)
        assertSame(intersectionPoints[0].obj, sphere)
        assertSame(intersectionPoints[1].obj, sphere)
    }

    @Test
    internal fun intersectingScaledSphereWithRay() {
        val r = ray(point(0, 0, -5), vector(0, 0, 1))
        val s = sphere().thenTransform(scaling(2, 2, 2))
        val xs = r.intersect(s)

        assertEquals(2, xs.size)
        assertEquals(3.0, xs[0].t)
        assertEquals(7.0, xs[1].t)
    }

    @Test
    internal fun intersectingTranslatedSphereWithRay() {
        val r = ray(point(0, 0, -5), vector(0, 0, 1))
        val s = sphere().thenTransform(translation(5, 0, 0))
        val xs = r.intersect(s)

        assertEquals(0, xs.size)
    }
}