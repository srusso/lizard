package net.sr89.lizard.math

import net.sr89.lizard.data.color
import net.sr89.lizard.data.intersection
import net.sr89.lizard.data.point
import net.sr89.lizard.data.pointLight
import net.sr89.lizard.data.ray
import net.sr89.lizard.data.vector
import net.sr89.lizard.emptyWorld
import net.sr89.lizard.objects.Sphere
import net.sr89.lizard.objects.sphere
import net.sr89.lizard.utils.DELTA
import net.sr89.lizard.world
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class RayCastingOperationsTest {
    @Test
    internal fun `precomputing the state of an intersection`() {
        val r = ray(point(0, 0, -5), vector(0, 0, 1))
        val s = sphere()
        val i = intersection(4, s)

        val comps = prepareComputations(i, r)

        assertEquals(i.t, comps.t)
        assertEquals(i.obj, comps.obj)
        assertEquals(point(0, 0, -1), comps.point)
        assertEquals(vector(0, 0, -1), comps.eyev)
        assertEquals(vector(0, 0, -1), comps.normalv)
    }

    @Test
    internal fun `intersection occurs on the outside`() {
        val r = ray(point(0, 0, -5), vector(0, 0, 1))
        val s = sphere()
        val i = intersection(4, s)

        val comps = prepareComputations(i, r)

        assertFalse(comps.inside)
    }

    @Test
    internal fun `intersection occurs on the inside`() {
        val r = ray(point(0, 0, 0), vector(0, 0, 1))
        val s = sphere()
        val i = intersection(1, s)

        val comps = prepareComputations(i, r)

        assertTrue(comps.inside)
        assertEquals(point(0, 0, 1), comps.point)
        assertEquals(vector(0, 0, -1), comps.eyev)
        assertEquals(vector(0, 0, -1), comps.normalv)
    }

    @Test
    internal fun `shading an intersection`() {
        val w = world()
        val r = ray(point(0, 0, -5), vector(0, 0, 1))
        val s = w[0]
        val i = intersection(4, s)

        val intersectionData = prepareComputations(i, r)
        val c = shadeHit(w, intersectionData)

        assertEquals(color(0.38066, 0.47583, 0.2855), c)
    }

    @Test
    internal fun `shading an intersection from the inside`() {
        val w = world().copy(light = pointLight(point(0, 0.25, 0), color(1, 1, 1)))
        val r = ray(point(0, 0, 0), vector(0, 0, 1))
        val s = w[1]
        val i = intersection(0.5, s)

        val intersectionData = prepareComputations(i, r)
        val c = shadeHit(w, intersectionData)

        assertEquals(color(0.90498, 0.90498, 0.90498), c)
    }

    @Test
    internal fun `intersection is shadowed`() {
        val s1 = sphere()
        val s2 = sphere()
            .thenTransform(translation(0, 0, 10))

        val w = emptyWorld()
            .copy(light = pointLight(point(0, 0, -10), color(1, 1, 1)))
            .plusObject(s1)
            .plusObject(s2)

        val r = ray(point(0, 0, 5), vector(0, 0, 1))
        val i = intersection(4, s2)

        val intersectionData = prepareComputations(i, r)
        val c = shadeHit(w, intersectionData)

        assertEquals(color(0.1, 0.1, 0.1), c)
    }

    @Test
    internal fun `color when a ray misses`() {
        val w = world()
        val r = ray(point(0, 0, -5), vector(0, 1, 0))
        val color = colorAt(w, r)

        assertEquals(color(0, 0, 0), color)
    }

    @Test
    internal fun `color when a ray hits`() {
        val w = world()
        val r = ray(point(0, 0, -5), vector(0, 0, 1))
        val color = colorAt(w, r)

        assertEquals(color(0.38066, 0.47583, 0.2855), color)
    }

    @Test
    internal fun `color with intersection behind ray`() {
        val w = world().mapIndexed { _, obj ->
            when(obj) {
                is Sphere -> obj.withMaterial(obj.material.copy(ambient = 1.0))
                else -> throw IllegalArgumentException()
            }
        }
        val inner = w[1]
        val r = ray(point(0, 0, 0.75), vector(0, 0, -1))
        val c = colorAt(w, r)

        assertEquals(inner.material().surfaceColor, c)
    }

    @Test
    internal fun `the hit should offset the point`() {
        val r = ray(point(0, 0, 5), vector(0, 0, 1))
        val s = sphere()
            .thenTransform(translation(0, 0, 1))
        val i = intersection(5, s)
        val comps = prepareComputations(i, r)

        assertTrue(comps.overPoint.z - comps.point.z < -DELTA / 2)
        assertTrue(comps.point.z > comps.overPoint.z)
    }
}