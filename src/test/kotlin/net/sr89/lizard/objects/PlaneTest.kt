package net.sr89.lizard.objects

import net.sr89.lizard.data.get
import net.sr89.lizard.data.point
import net.sr89.lizard.data.ray
import net.sr89.lizard.data.vector
import net.sr89.lizard.math.intersect
import net.sr89.lizard.math.intersectWith
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PlaneTest {
    @Test
    internal fun `the normal of a plan is a constant everywhere`() {
        val p = plane()

        assertEquals(vector(0, 1, 0), p.localNormalAt(point(0, 0, 0)))
        assertEquals(vector(0, 1, 0), p.localNormalAt(point(10, 0, -10)))
        assertEquals(vector(0, 1, 0), p.localNormalAt(point(-5, 0, 150)))
    }

    @Test
    internal fun `intersect with a ray parallel to the plane`() {
        val p = plane()
        val r = ray(point(0, 10, 0), vector(0, 0, 1))
        val intersections = r.intersectWith(p)
        assertEquals(0, intersections.size)
    }

    @Test
    internal fun `intersect with a ray coplanar to the plane`() {
        val p = plane()
        val r = ray(point(0, 0, 0), vector(0, 0, 1))
        val intersections = r.intersectWith(p)
        assertEquals(0, intersections.size)
    }

    @Test
    internal fun `ray intersecting plane from above`() {
        val p = plane()
        val r = ray(point(0, 1, 0), vector(0, -1, 0))
        val intersections = r.intersectWith(p)

        assertEquals(1, intersections.size)
        assertEquals(1.0, intersections[0].t)
        assertSame(p, intersections[0].obj)
    }

    @Test
    internal fun `ray intersecting plane from below`() {
        val p = plane()
        val r = ray(point(0, -1, 0), vector(0, 1, 0))
        val intersections = r.intersectWith(p)

        assertEquals(1, intersections.size)
        assertEquals(1.0, intersections[0].t)
        assertSame(p, intersections[0].obj)
    }
}