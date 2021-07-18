package net.sr89.lizard.data

import net.sr89.lizard.objects.sphere
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IntersectionTest {
    @Test
    internal fun createIntersection() {
        val sphere = sphere()
        val intersection = intersection(3.5, sphere)

        assertEquals(3.5, intersection.t)
        assertTrue(intersection.obj === sphere)
    }

    @Test
    internal fun createIntersections() {
        val sphere = sphere()
        val i1 = intersection(1, sphere)
        val i2 = intersection(2, sphere)

        val intersections = intersections(i1, i2)

        assertEquals(2, intersections.size)
        assertEquals(1.0, intersections[0].t)
        assertEquals(2.0, intersections[1].t)
    }

    @Test
    internal fun hitAmongPositiveIntersections() {
        val sphere = sphere()
        val i1 = intersection(1, sphere)
        val i2 = intersection(2, sphere)

        val intersections = intersections(i1, i2)

        assertSame(i1, intersections.hit())
    }

    @Test
    internal fun hitAmongPositiveAndNegativeIntersections() {
        val sphere = sphere()
        val i1 = intersection(-1, sphere)
        val i2 = intersection(2, sphere)

        val intersections = intersections(i1, i2)

        assertSame(i2, intersections.hit())
    }

    @Test
    internal fun hitAmongNegativeIntersections() {
        val sphere = sphere()
        val i1 = intersection(-1, sphere)
        val i2 = intersection(-2, sphere)

        val intersections = intersections(i1, i2)

        assertNull(intersections.hit())
    }

    @Test
    internal fun hitAmongUnsortedIntersections() {
        val sphere = sphere()
        val i1 = intersection(5, sphere)
        val i2 = intersection(7, sphere)
        val i3 = intersection(-3, sphere)
        val i4 = intersection(2, sphere)

        val intersections = intersections(i1, i2, i3, i4)

        assertSame(i4, intersections.hit())
    }
}